package com.wangrj.yunpan.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * by wangrongjun on 2017/8/26.
 */
public class User {

    @Id
    @GeneratedValue
    private Integer userId;
    @Column(length = 20, nullable = false, unique = true)
    private String username;
    private Integer gender;
    private Date registerDateTime;

    public User(String username, Integer gender, Date registerDateTime) {
        this.username = username;
        this.gender = gender;
        this.registerDateTime = registerDateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getRegisterDateTime() {
        return registerDateTime;
    }

    public void setRegisterDateTime(Date registerDateTime) {
        this.registerDateTime = registerDateTime;
    }
}
