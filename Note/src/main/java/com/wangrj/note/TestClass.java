package com.wangrj.note;

import com.wangrj.java_lib.java_util.GsonUtil;
import com.wangrj.java_lib.java_util.HttpRequest;

public class TestClass {

    public static void main(String[] args) {
        HttpRequest.Response response = new HttpRequest().
                setRequestMethod("POST").
                setRequestBody("content=note1".getBytes()).
                request("http://localhost:8080/note");

        System.out.println(response.getResponseCode());
        System.out.println(response.getResponseData());
    }

}
