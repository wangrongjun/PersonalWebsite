package com.wangrj.yunpan.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * by wangrongjun on 2017/8/28.
 */
public class Directory {

    @Id
    @GeneratedValue
    private Integer directoryId;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Directory parent;// 自身外键
    @ManyToOne
    private User owner;
    private Date createDateTime;

    public Directory(String name, Directory parent, User owner, Date createDateTime) {
        this.name = name;
        this.parent = parent;
        this.owner = owner;
        this.createDateTime = createDateTime;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
