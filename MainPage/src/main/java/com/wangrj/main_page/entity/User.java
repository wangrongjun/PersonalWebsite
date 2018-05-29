package com.wangrj.main_page.entity;

/**
 * by wangrongjun on 2018/5/28.
 */
//@Entity
public class User {

//    @Id
//    @GeneratedValue
    private Integer userId;
    private String username;

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}
