package com.wangrj.note.config;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

/**
 * by wangrongjun on 2018/9/18.
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler({
            ServletException.class,
            TypeMismatchException.class,
            ConstraintViolationException.class,
    })
    public ResponseEntity<String> handleParamErrorException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
    }

}
