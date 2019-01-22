package com.wangrj.yunpan.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.File;

@Configuration
public class MkdirYunPanListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${rootPath}")
    private String rootPath;
    @Value("${tempPath}")
    private String tempPath;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Check dir...");
        File rootFile = new File(rootPath);
        File tempFile = new File(tempPath);
        if (!rootFile.exists()) {
            if (!rootFile.mkdirs()) {
                throw new IllegalStateException(rootPath + " mkdirs fail");
            }
        }
        if (!tempFile.exists()) {
            if (!tempFile.mkdirs()) {
                throw new IllegalStateException(tempFile + " mkdirs fail");
            }
        }
        logger.info("dir created!");
    }

}
