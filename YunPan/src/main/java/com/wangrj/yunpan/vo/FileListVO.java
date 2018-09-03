package com.wangrj.yunpan.vo;

import com.wangrj.yunpan.bean.FileItem;

import java.util.List;

/**
 * by wangrongjun on 2018/9/3.
 */
public class FileListVO {

    private List<FileItem> fileItemList;
    private List<String> navigateList;
    private String rootPath;

    public FileListVO() {
    }

    public FileListVO(List<FileItem> fileItemList, List<String> navigateList) {
        this.fileItemList = fileItemList;
        this.navigateList = navigateList;
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
}
