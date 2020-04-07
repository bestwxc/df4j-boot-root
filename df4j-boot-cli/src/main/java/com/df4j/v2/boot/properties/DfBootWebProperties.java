package com.df4j.v2.boot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SpringBoot Web配置
 */
@ConfigurationProperties(prefix = "df.boot.web")
public class DfBootWebProperties {
    private boolean openCors;

    private boolean openHttpDebugLog;

    // 打印警告级别预警日志的最小时间
    private Long printWarnLogThreshold = 800L;
    // 打印错误级别预警日志的最小时间
    private Long printErrorLogThreshold = 800L;

    private String securityType;

    public boolean isOpenCors() {
        return openCors;
    }

    public void setOpenCors(boolean openCors) {
        this.openCors = openCors;
    }

    public boolean isOpenHttpDebugLog() {
        return openHttpDebugLog;
    }

    public void setOpenHttpDebugLog(boolean openHttpDebugLog) {
        this.openHttpDebugLog = openHttpDebugLog;
    }

    public Long getPrintWarnLogThreshold() {
        return printWarnLogThreshold;
    }

    public void setPrintWarnLogThreshold(Long printWarnLogThreshold) {
        this.printWarnLogThreshold = printWarnLogThreshold;
    }

    public Long getPrintErrorLogThreshold() {
        return printErrorLogThreshold;
    }

    public void setPrintErrorLogThreshold(Long printErrorLogThreshold) {
        this.printErrorLogThreshold = printErrorLogThreshold;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityType() {
        return securityType;
    }
}
