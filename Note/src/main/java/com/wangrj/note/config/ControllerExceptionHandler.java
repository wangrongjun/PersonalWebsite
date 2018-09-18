package com.wangrj.note.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * by wangrongjun on 2018/9/18.
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                contentType(MediaType.TEXT_PLAIN).
                body(e.toString());
    }

}
