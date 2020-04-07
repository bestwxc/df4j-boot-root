package com.df4j.v2.boot.web.rpc;

import com.df4j.v2.base.constant.DfBizErrorCode;
import com.df4j.v2.base.exception.BizException;
import com.df4j.v2.base.exception.DfException;
import com.df4j.v2.base.rpc.Context;
import com.df4j.v2.base.rpc.Function;
import com.df4j.v2.base.rpc.Result;
import com.df4j.v2.base.util.MapUtils;
import com.df4j.v2.base.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * Function的抽象类，实现部分公共逻辑
 */
public abstract class AbstractFunction implements Function {

    private Logger logger = LoggerFactory.getLogger(AbstractFunction.class);

    @Override
    public void invoke(Context context) {
        SimpleRequestImpl request = (SimpleRequestImpl) context.getRequest();
        SimpleResponseImpl response = (SimpleResponseImpl) context.getResponse();
        Result result = null;
        try {
            Map map = request.getRequestMap();
            String token = MapUtils.getStringFromMap(map, "df4j-token", null);
            // 校验权限
            this.checkPermission(request.getGroupId(), request.getFuncNo(), token);
            result = this.invoke(map);
        }catch (Throwable t){
            result = this.hendlerException(t);
        }
        response.setResult(request);
        response.setErrorInfo(result.getErrorInfo());
        response.setErrorNo(result.getErrorNo());
    }

    /**
     * 校验权限
     * @param groupId
     * @param funcNo
     * @param token
     */
    private void checkPermission(String groupId, String funcNo, String token){

    }

    /**
     * 统一处理异常
     * @param t
     * @return
     */
    private Result hendlerException(Throwable t){
        Integer errorNo = null;
        String errorInfo = null;
        if (t instanceof BizException) {
            BizException be = (BizException) t;
            errorNo = be.getErrorNo();
            errorInfo = be.getMessage();
            logger.info("业务异常，errorNo:{},errorInfo:{}", errorNo, errorInfo, t);
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


    /**
     * 抽象方法，供业务逻辑实现
     * @param map
     * @return
     */
    abstract Result invoke(Map map);
}
