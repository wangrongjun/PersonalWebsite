package com.wangrj.note.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wangrj.java_lib.java_util.DateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * by wangrongjun on 2018/9/18.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 开启Validation校验机制
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public Converter<String, Date> dateConverter() {
        // 一旦使用lambda表达式就报错：IllegalArgumentException:
        // Unable to determine source type <S> and target type <T> for your Converter
        // [com.wangrj.note.config.WebMvcConfig$$Lambda$523/834864210]; does the class parameterize those types?
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                return DateUtil.toDate(source);
            }
        };
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter =
                new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        List<MediaType> mediaTypeList = Collections.singletonList(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
        converters.add(fastJsonHttpMessageConverter);
    }

}
