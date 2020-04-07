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

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    public DfSpringApplicationRunListener(SpringApplication application, String[] args){
        this.application = application;
        this.args = args;
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        LoggerManage.getSysLogger().info("开始启动业务程序");
        startTime.set(System.currentTimeMillis());
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().info("当前业务程序contextPrepared");
    }

    @Override
    public void starting() {
        LoggerManage.getSysLogger().info("当前业务程序starting");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().info("当前业务程序contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().info("当前业务程序started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        LoggerManage.getSysLogger().info("当前业务程序启动完成，进入running状态，耗时{}ms。",
                System.currentTimeMillis() - startTime.get());
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        LoggerManage.getSysLogger().info("当前业务程序启动失败，耗时{}ms",
                System.currentTimeMillis() - startTime.get());
    }
}