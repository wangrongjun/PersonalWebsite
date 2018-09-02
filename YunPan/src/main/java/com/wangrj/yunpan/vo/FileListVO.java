package com.wangrj.yunpan.vo;

import com.wangrj.yunpan.bean.FileItem;

import java.util.List;

/**
 * by wangrongjun on 2018/9/3.
 */
public class FileListVO {

    private List<FileItem> fileItemList;
    private List<String> navigateList;
    private String encodedPath;
    private String relativePath;

    public FileListVO() {
    }

    public FileListVO(List<FileItem> fileItemList, List<String> navigateList, String encodedPath, String relativePath) {
        this.fileItemList = fileItemList;
        this.navigateList = navigateList;
        this.encodedPath = encodedPath;
        this.relativePath = relativePath;
    }

    public List<FileItem> getFileItemList() {
        return fileItemList;
    }

    public void setFileItemList(List<FileItem> fileItemList) {
        this.fileItemList = fileItemList;
    }

    public List<String> getNavigateList() {
        return navigateList;
    }

    public void setNavigateList(List<String> navigateList) {
        this.navigateList = navigateList;
    }

    public String getEncodedPath() {
        return encodedPath;
    }

    public void setEncodedPath(String encodedPath) {
        this.encodedPath = encodedPath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
