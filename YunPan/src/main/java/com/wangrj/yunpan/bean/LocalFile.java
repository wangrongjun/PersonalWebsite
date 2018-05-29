package com.wangrj.yunpan.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * by wangrongjun on 2017/8/27.
 */
public class LocalFile {

    @Id
    @GeneratedValue
    private Integer fileItemId;
    private String messageDigest;
    private String path;

    public LocalFile(String messageDigest, String path) {
        this.messageDigest = messageDigest;
        this.path = path;
    }

    public Integer getFileItemId() {
        return fileItemId;
    }

    public void setFileItemId(Integer fileItemId) {
        this.fileItemId = fileItemId;
    }

    public String getMessageDigest() {
        return messageDigest;
    }

    public void setMessageDigest(String messageDigest) {
        this.messageDigest = messageDigest;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
