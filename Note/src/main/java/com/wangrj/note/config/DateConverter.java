package com.wangrj.note.config;

import com.wangrj.java_lib.java_util.DateUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * by wangrongjun on 2018/9/19.
 */
@Configuration
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        return DateUtil.toDate(source);
    }
}
