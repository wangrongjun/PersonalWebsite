package com.wangrj.yunpan.bean;

import com.wangrj.java_lib.java_util.DateUtil;
import com.wangrj.java_lib.java_util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * by wangrongjun on 2017/8/24.
 */
public class FileItem {

    private String name;
    private String iconUrl;
    private String size;
    private String lastModifiedDate;
    private boolean directory;

    public static List<FileItem> toFileItemList(List<File> fileList) {
        if (fileList == null) {
            return null;
        }

        List<FileItem> fileItemList = new ArrayList<>();
        for (File file : fileList) {
            FileItem fileItem = new FileItem();
            fileItem.name = file.getName();
            fileItem.size = TextUtil.transferFileLength(file.length(), 2);
            fileItem.lastModifiedDate = DateUtil.toDateTimeText(new Date(file.lastModified()));
            fileItem.iconUrl = "img/file_icon/" + getTypeName(file) + ".png";
            fileItem.directory = file.isDirectory();
            fileItemList.add(fileItem);
        }

        return fileItemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    private static String getTypeName(File file) {
        if (file.isDirectory()) {
            return "directory";
        }
        String extendName = TextUtil.getTextAfterLastPoint(file.getName());
        switch (extendName) {
            case "png":
            case "jpg":
            case "jpeg":
            case "gif":
            case "bmp":
            case "ico":
                return "image";
            case "txt":
            case "ini":
                return "text";
            case "mp3":
            case "wma":
                return "music";
            case "flv":
            case "mp4":
            case "avi":
            case "wmv":
            case "mkv":
            case "mov":
                return "video";
            case "pdf":
                return "pdf";
            case "ppt":
            case "pptx":
                return "word";
            case "xls":
            case "xlsx":
                return "excel";
            case "doc":
            case "docx":
                return "word";
            case "zip":
            case "rar":
                return "zip";
            default:
                return "unknow";
        }
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
}
