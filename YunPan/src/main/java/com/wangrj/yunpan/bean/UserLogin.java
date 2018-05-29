package com.wangrj.yunpan.bean;

import jdk.nashorn.internal.ir.annotations.Reference;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * by wangrongjun on 2017/8/28.
 */
public class UserLogin {

    @Id
    @GeneratedValue
    private Integer userLoginId;
    @Reference
    private User user;
    @Column(length = 20, nullable = false)
    private String password;
    private Date latestLoginDateTime;
    private String latestLoginIp;

    public UserLogin(User user, String password, Date latestLoginDateTime, String latestLoginIp) {
        this.user = user;
        this.password = password;
        this.latestLoginDateTime = latestLoginDateTime;
        this.latestLoginIp = latestLoginIp;
    }

    public Integer getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLatestLoginDateTime() {
        return latestLoginDateTime;
    }

    public void setLatestLoginDateTime(Date latestLoginDateTime) {
        this.latestLoginDateTime = latestLoginDateTime;
    }

    public String getLatestLoginIp() {
        return latestLoginIp;
    }

    public void setLatestLoginIp(String latestLoginIp) {
        this.latestLoginIp = latestLoginIp;
    }
}
