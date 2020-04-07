package com.df4j.v2.boot.listener;

import com.df4j.v2.base.log.LoggerManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Df默认的生命周期监听器，用来打印程序状态
 */
public class DfSpringApplicationRunListener implements SpringApplicationRunListener {

    private Logger logger = LoggerFactory.getLogger(DfSpringApplicationRunListener.class);

    private SpringApplication application;
    private String[] args;

    public DfSpringApplicationRunListener(SpringApplication application, String[] args){
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        logger.error("当前程序starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        LoggerManage.getSysLogger().error("当前程序environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().error("当前程序contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().error("当前程序contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().error("当前程序started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().error("当前程序running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        LoggerManage.getSysLogger().error("当前程序failed");
    }
}
