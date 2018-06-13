package com.wangrj.note.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Validated
public class BaseController {

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    @ResponseBody
    public String handleExceptionMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String name = e.getName();
        Object value = e.getValue();
//        String message = e.getMessage();
        Class requiredType = e.getRequiredType();
        String msg = name + " : " + "不能把 '" + value + "' 转换成 " + requiredType.getName() + " 类型";
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : constraintViolations) {
            builder.append(violation.getInvalidValue()).append(" : ").append(violation.getMessage()).append("\r\n");
        }
        return new ResponseEntity<>(builder.toString(), HttpStatus.BAD_REQUEST);
    }

}
