package com.wangrj.note;

import com.wangrj.java_lib.java_util.HttpRequest;
import com.wangrj.java_lib.java_util.StreamUtil;

import java.io.IOException;
import java.io.InputStream;

public class TestClass {

    public static void main(String[] args) throws IOException, HttpRequest.ResponseCodeNot200Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("note.json");
        String json = StreamUtil.readInputStream(is);

        HttpRequest.Response response = new HttpRequest().
                setRequestMethod("POST").
                returnResponseHeader(true).
                setRequestHeader("Content-Type", "application/json").
                setRequestBody(json).
                request("http://localhost:8080/note/importFromJson");
        System.out.println(response.toResponseHeaderString());
        System.out.println(response.toResponseText().replace("\\n", "\n"));
    }

}
