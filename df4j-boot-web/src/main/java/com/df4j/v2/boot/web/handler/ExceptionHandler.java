package com.df4j.v2.boot.web.handler;


import com.df4j.v2.base.constant.DfBizErrorCode;
import com.df4j.v2.base.exception.BizException;
import com.df4j.v2.base.exception.DfException;
import com.df4j.v2.base.rpc.Result;
import com.df4j.v2.base.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public Result handler(Throwable t) {
        Integer errorNo = null;
        String errorInfo = null;
        if (t instanceof BizException) {
            BizException be = (BizException) t;
            errorNo = be.getErrorNo();
            errorInfo = be.getMessage();
            logger.info("业务异常，errorNo:{},errorInfo:{}", errorNo, errorInfo, t);
        } else if (t instanceof HttpMessageNotReadableException) {
            errorNo = DfBizErrorCode.REQUEST_FORMAT_ERROR;
            errorInfo = "请求接口格式不正确";
            logger.warn("errorNo:{},errorInfo:{}", errorNo, errorInfo, t);
        } else if (t instanceof DfException) {
            errorNo = DfBizErrorCode.UNCATCH_BUSINESS_EXCEPTION;
            errorInfo = t.getMessage();
            logger.error("errorNo:{},errorInfo:{}", errorNo, errorInfo, t);
        } else {
            errorNo = DfBizErrorCode.SYSTEM_ERROR;
            errorInfo = "系统异常:" + t.getMessage();
            logger.error("errorNo:{},errorInfo:{}", errorNo, errorInfo, t);
        }
        return ResultUtils.error(errorNo, errorInfo);
    }
}
