package com.wangrj.yunpan.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * by wangrongjun on 2017/8/27.
 */
public class YunPanFile {

    @Id
    @GeneratedValue
    private Integer yunPanFileId;
    @ManyToOne
    private User user;
    @ManyToOne
    private LocalFile localFile;
    private Directory directory;
    private Date createDate;

    public YunPanFile(User user, LocalFile localFile, Directory directory, Date createDate) {
        this.user = user;
        this.localFile = localFile;
        this.directory = directory;
        this.createDate = createDate;
    }

    public Integer getYunPanFileId() {
        return yunPanFileId;
    }

    public void setYunPanFileId(Integer yunPanFileId) {
        this.yunPanFileId = yunPanFileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalFile getLocalFile() {
        return localFile;
    }

    public void setLocalFile(LocalFile localFile) {
        this.localFile = localFile;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
