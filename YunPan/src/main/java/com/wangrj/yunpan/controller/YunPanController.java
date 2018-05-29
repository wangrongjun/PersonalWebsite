package com.wangrj.yunpan.controller;

import com.wangrj.java_lib.java_util.*;
import com.wangrj.java_lib.math.sort.SortHelper;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * by wangrongjun on 2018/5/30.
 */
@Controller
public class YunPanController {

    @RequestMapping("/createDirectory")
    public String createDirectory(HttpServletRequest request, String dirName, String encodedPath) {
        String path = CharsetUtil.decode(encodedPath);
        String realDirPath = getRealPath(request, "/admin/file" + path + dirName);
        boolean succeed = new File(realDirPath).mkdirs();
        if (!succeed) {
            System.out.println("directory " + realDirPath + " create failed!");
        }
//        return "-" + request.getHeader("Referer");
//        return "-showFileList.do?encodedPath=" + encodedPath;
        return "test";
    }

    @RequestMapping("/deleteFileOrDir")
    public String deleteFileOrDir(HttpServletRequest request, String encodedPath) {
        String path = CharsetUtil.decode(encodedPath);
        String fileDir = getRealPath(request, "/admin/file" + path);

        String password = request.getParameter("password");
        System.out.println("password: " + password);
        password = DataUtil.md5(password);
        System.out.println("password(md5): " + password);
        if ("n0TpVuOit7VZjGJfzIAsNg==".equals(password)) {//密码2143的md5摘要，输入正确才能删除文件
            String s = request.getParameter("fileNameList");
            System.out.println(s);
            String[] fileNameList = s.split("_d_i_v_i_d_e_r_");

            for (String fileName : fileNameList) {
                if (!TextUtil.isEmpty(fileName)) {
                    System.out.println("执行文件或目录的删除操作：" + fileDir + fileName);
                    File deleteFile = new File(fileDir + fileName);
                    if (deleteFile.isDirectory()) {
                        FileUtil.deleteDir(deleteFile);
                    } else {
                        FileUtil.delete(fileDir + fileName);
                    }
                }
            }
        }

//        return "-" + request.getHeader("Referer");
        return "test";
    }

    @RequestMapping("/downloadFolder")
    @ResponseBody
    public String downloadFolder(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String folderPath) throws IOException {
        folderPath = CharsetUtil.decode(folderPath);// folderPath="/abc/def/"
        String realFolderPath = getRealPath(request, "/admin/file" + folderPath);
        System.out.println("Zip realFolderPath: " + realFolderPath);
        File folder = new File(realFolderPath);
        if (!folder.exists()) {
            response.getWriter().write("folder not exists");
            response.setStatus(404);
            return null;
        }

        String zipFileName;
        int id = 1;
        do {
            zipFileName = (id++) + ".zip";
        } while (new File(getRealPath(request, "/admin/temp/" + zipFileName)).exists());
        ZipUtil.compress(realFolderPath, getRealPath(request, "/admin/temp/" + zipFileName));

        return "admin/temp/" + zipFileName;
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

    @GetMapping("/showFileList")
    public String showFileList(HttpServletRequest request,
                               @RequestParam(defaultValue = "0") int sortType,
                               @RequestParam(defaultValue = "/") String encodedPath) {
        if (sortType >= SortType.values().length) {
            throw new IllegalArgumentException("sortType " + sortType + " not exists");
        }

        String path = CharsetUtil.decode(encodedPath);// path的第一个和最后一个字符都为反斜杠
        String relativePath = "/admin/file" + path;
        String currentPath = getRealPath(request, relativePath);

        // 获取目录列表
        File[] files = new File(currentPath).listFiles(File::isDirectory);
        if (files == null) {
            files = new File[0];
        }
        List<File> dirList = Arrays.asList(files);

        // 获取文件列表
        files = new File(currentPath).listFiles(File::isDirectory);
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
        List<String> addressList = new ArrayList<>();
        for (String address : strings) {
            if (!TextUtil.isEmpty(address)) {
                addressList.add(address);
            }
        }

        // 合并目录列表和文件列表
        List<File> dirFileList = new ArrayList<>();
        dirFileList.addAll(dirList);
        dirFileList.addAll(fileList);

        request.setAttribute("fileItemList", com.wangrj.yunpan.view.FileItem.toFileItemList(dirFileList));
        request.setAttribute("addressList", addressList);
        request.setAttribute("encodedPath", CharsetUtil.encode(encodedPath));
        request.setAttribute("relativePath", relativePath);

        return "file.jsp";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(HttpServletRequest request) {
//        request.setCharacterEncoding("utf-8");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            // TODO
//            List<FileItem> fileItemList = sfu.parseRequest(request);
            List<FileItem> fileItemList = null;

            FileItem encodedPathItem = fileItemList.get(0);
            String path = CharsetUtil.decode(encodedPathItem.getString());
            String fileDir = getRealPath(request, "/admin/file" + path);
            LogUtil.print(fileDir);

            FileItem uploadFileItem = fileItemList.get(1);
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
