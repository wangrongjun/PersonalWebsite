package com.wangrj.yunpan.controller;

import com.wangrj.java_lib.java_util.*;
import com.wangrj.java_lib.math.sort.SortHelper;
import com.wangrj.yunpan.bean.ApiResponse;
import com.wangrj.yunpan.bean.FileItem;
import com.wangrj.yunpan.vo.FileListVO;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * by wangrongjun on 2018/5/30.
 */
@Controller
public class YunPanController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${rootPath}")
    private String rootPath;
    @Value("${tempPath}")
    private String tempPath;

    @GetMapping("/showFileList")
    public ResponseEntity<ApiResponse<FileListVO>> showFileList(
            @RequestParam(defaultValue = "0") int sortType,
            @RequestParam(defaultValue = "/") String path) {

        if (sortType >= SortType.values().length) {
            throw new IllegalArgumentException("sortType " + sortType + " not exists");
        }

        // 获取目录列表
        File[] files = new File(rootPath + path).listFiles(File::isDirectory);
        if (files == null) {
            files = new File[0];
        }
        List<File> dirList = Arrays.asList(files);

        // 获取文件列表
        files = new File(rootPath + path).listFiles(file -> !file.isDirectory());
        if (files == null) {
            files = new File[0];
        }
        List<File> fileList = Arrays.asList(files);

        // 对目录和文件按条件排序
        SortHelper.sortInsertion(fileList, (file1, file2) -> {
            switch (SortType.values()[sortType]) {
                case SORT_BY_NAME:
                    return TextUtil.compareChinaPinYin(file1.getName(), file2.getName());
                case SORT_BY_NAME_DESC:
                    return TextUtil.compareChinaPinYin(file2.getName(), file1.getName());
                case SORT_BY_SIZE:
                    return file1.length() <= file2.length() ? -1 : 1;
                case SORT_BY_SIZE_DESC:
                    return file1.length() > file2.length() ? -1 : 1;
                case SORT_BY_TIME:
                    return file1.lastModified() <= file2.lastModified() ? -1 : 1;
                case SORT_BY_TIME_DESC:
                    return file1.lastModified() > file2.lastModified() ? -1 : 1;
                default:
                    return 0;
            }
        });

        // 根据path获取每一级的地址（页面的地址索引）
        String[] strings = path.split("/");
        List<String> navigateList = new ArrayList<>();
        for (String navigate : strings) {
            if (!TextUtil.isEmpty(navigate)) {
                navigateList.add(navigate);
            }
        }

        // 合并目录列表和文件列表
        List<File> dirFileList = new ArrayList<>();
        dirFileList.addAll(dirList);
        dirFileList.addAll(fileList);

        FileListVO vo = new FileListVO(FileItem.toFileItemList(dirFileList), navigateList, path, path);
        return ResponseEntity.ok(ApiResponse.data(vo));
    }

    @RequestMapping("/createDirectory")
    public String createDirectory(String dirName, String parent) {
        File newDir = new File(rootPath + parent + dirName);
        logger.info("创建新目录：" + newDir.getAbsolutePath());
        boolean succeed = newDir.mkdirs();
        if (!succeed) {
            logger.info("创建新目录失败：" + newDir.getAbsolutePath());
        }
//        return "-" + request.getHeader("Referer");
//        return "-showFileList.do?encodedPath=" + encodedPath;
        return "test";
    }

    @RequestMapping("/deleteFileOrDir")
    public ResponseEntity<ApiResponse> deleteFileOrDir(String parentPath, String password, String fileNames) {
        logger.info("password: " + password);
        password = DataUtil.md5(password);
        logger.info("password(md5): " + password);
        if ("n0TpVuOit7VZjGJfzIAsNg==".equals(password)) {//密码2143的md5摘要，输入正确才能删除文件
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("密码错误"));
        }

        File parentDir = new File(rootPath + parentPath);
        logger.info("准备删除的文件（目录）列表：" + fileNames);
        String[] fileNameList = fileNames.split("_d_i_v_i_d_e_r_");

        for (String fileName : fileNameList) {
            if (!TextUtil.isEmpty(fileName)) {
                File deleteFile = new File(parentDir + fileName);
                logger.info("执行文件（目录）的删除操作：" + deleteFile.getAbsolutePath());
                if (deleteFile.isDirectory()) {
                    FileUtil.deleteDir(deleteFile);
                } else {
                    deleteFile.delete();
                }
            }
        }

        logger.info("已删除以下文件（目录）：" + fileNames);
        return ResponseEntity.ok(ApiResponse.ok("删除成功"));
    }

    @GetMapping("/downloadFolder")
    public ResponseEntity<ApiResponse<String>> downloadFolder(String folderPath) {
        File folder = new File(rootPath + folderPath);

//        String realFolderPath = getRealPath(request, "/admin/file" + folderPath);

        logger.info("开始压缩目录：" + folder.getAbsolutePath());
        if (!folder.exists()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("目录不存在"));
        }


        String zipFileName = folder.getName() + ".zip";
        String zipFilePath = new File(tempPath + zipFileName).getAbsolutePath();
        int id = 1;
        while (new File(zipFilePath).exists()) {
            logger.info("temp目录下已有同名压缩包：" + zipFilePath);
            zipFileName = folder.getName() + (id++) + ".zip";
            zipFilePath = new File(tempPath + zipFileName).getAbsolutePath();
            logger.info("使用新的压缩包名字：" + zipFilePath);
        }

        ZipUtil.compress(folder.getAbsolutePath(), zipFilePath);
        logger.info("成功压缩目录 " + folder.getAbsolutePath() + " 到压缩包 " + zipFilePath);

        return ResponseEntity.ok(ApiResponse.data("/" + tempPath + zipFileName));
    }

    @GetMapping("/getFolderList")
    @ResponseBody
    public String getFolderList(HttpServletRequest request) {
        // 获取目录列表
        String path = getRealPath(request, "/admin/file");
        Folder folder = getFolder(new File(path), "/");
        folder.folderName = "root";
        return GsonUtil.toPrettyJson(folder);
    }

    private static Folder getFolder(File folder, String path) {
        if (folder == null || !folder.exists()) {
            return null;
        }

        File[] childrenFile = folder.listFiles(File::isDirectory);

        List<Folder> childrenFolder = new ArrayList<>();
        if (childrenFile != null && childrenFile.length > 0) {
            for (File childFile : childrenFile) {
                childrenFolder.add(getFolder(childFile, path + childFile.getName() + "/"));
            }
        }

        return new Folder(folder.getName(), path, childrenFolder);
    }

    public static class Folder {
        String folderName;
        String path;
        List<Folder> children;

        Folder(String folderName, String path, List<Folder> children) {
            this.folderName = folderName;
            this.path = path;
            this.children = children;
        }
    }

    @RequestMapping("/moveFile")
    @ResponseBody
    public String moveFile(HttpServletRequest request, String filePath, String moveToPath) {
        filePath = CharsetUtil.decode(filePath);// "%2Fa%2Fb%2Fc.txt" to "/a/b/c.txt"
        moveToPath = CharsetUtil.decode(moveToPath);// "%2Fa%2Fb%2Fc%2F" to "/a/b/c/"

        String rootPath = getRealPath(request, "/admin/file");
        String fromPath = rootPath + filePath;
        String toPath = rootPath + moveToPath + TextUtil.getTextAfterLastSlash(filePath);
        System.out.println("fromPath: " + fromPath);
        System.out.println("toPath: " + toPath);

        boolean succeed = new File(fromPath).renameTo(new File(toPath));
        return succeed ? "succeed" : "file already exists";
    }

    private enum SortType {
        SORT_BY_NAME,
        SORT_BY_NAME_DESC,
        SORT_BY_TIME,
        SORT_BY_TIME_DESC,
        SORT_BY_SIZE,
        SORT_BY_SIZE_DESC
    }

    @PostMapping("/uploadFile")
    public String uploadFile(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            // TODO
//            List<FileItem> fileItemList = sfu.parseRequest(request);
            List<org.apache.tomcat.util.http.fileupload.FileItem> fileItemList = null;

            org.apache.tomcat.util.http.fileupload.FileItem encodedPathItem = fileItemList.get(0);
            String path = CharsetUtil.decode(encodedPathItem.getString());
            String fileDir = getRealPath(request, "/admin/file" + path);
            LogUtil.print(fileDir);

            org.apache.tomcat.util.http.fileupload.FileItem uploadFileItem = fileItemList.get(1);
            long totalSize = uploadFileItem.getSize();
            String fileName = TextUtil.getTextAfterLastSlash(uploadFileItem.getName());
            if (TextUtil.isEmpty(fileName)) {
                System.out.println("File name is null!!!");
                return "-" + request.getHeader("Referer");
            }

            System.out.println("------- Start Upload File: " + fileDir + fileName + " ---------");

            InputStream is = uploadFileItem.getInputStream();
            FileOutputStream fos = new FileOutputStream(fileDir + fileName);
            byte[] b = new byte[1024];
            int len;
            long previousTime = System.currentTimeMillis();
            long currentSize = 0;
            while ((len = is.read(b)) != -1) {
                fos.write(b, 0, len);
                currentSize += len;
                if (System.currentTimeMillis() - previousTime >= 1000) {//过了一秒钟
                    previousTime = System.currentTimeMillis();
                }
            }
            is.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("---------- Upload File Finish -----------");

        return "-" + request.getHeader("Referer");
    }

    private String getRealPath(HttpServletRequest request, String path) {
        return request.getServletContext().getRealPath(path);
    }

}
