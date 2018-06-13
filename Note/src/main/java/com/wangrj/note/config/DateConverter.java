package com.wangrj.note.config;

import com.wangrj.java_lib.java_util.DateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        return DateUtil.toDate(source);
    }
}
