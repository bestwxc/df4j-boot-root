package com.df4j.v2.boot.web.rpc;

import com.df4j.v2.base.constant.DfBizErrorCode;
import com.df4j.v2.base.rpc.Context;
import com.df4j.v2.base.rpc.Function;
import com.df4j.v2.base.rpc.Request;
import com.df4j.v2.base.rpc.Result;
import com.df4j.v2.base.util.MapUtils;
import com.df4j.v2.base.util.ResultUtils;
import com.df4j.v2.base.util.SpringUtils;
import com.df4j.v2.base.util.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RpcHandlerController {

    private Logger logger = LoggerFactory.getLogger(RpcHandlerController.class);

    @RequestMapping("/df4j/rpc/json")
    public Result handlerRpc(@RequestBody Map<String, ?> map, @RequestHeader("df4j-token") String token){
        // 获取groupId,用于未来扩展分组拦截
        String groupId = MapUtils.getStringFromMap(map, "groupId", "df4j");
        // 获取功能号
        String funcNo = MapUtils.getStringFromMap(map, "funcNo", null);
        if(ValidatorUtils.isEmpty(funcNo)){
            ResultUtils.error(DfBizErrorCode.REQUEST_FORMAT_ERROR, "请求参数中未找到功能号");
        }
        // 获取Function实现类的Bean类
        String functionBeanName = this.getFunctionBeanName(groupId, funcNo);
        // 获取Function实现实例
        Function function = SpringUtils.getBean(functionBeanName, Function.class);
        // 校验function实例是否存在
        if(ValidatorUtils.isNull(function)){
            ResultUtils.error(DfBizErrorCode.REQUEST_FORMAT_ERROR, "未找到功能号对应的Function实现");
        }
        Request request = this.prepareRequest(groupId, funcNo, token, map);
        SimpleResponseImpl response = new SimpleResponseImpl();
        Context context = new SimpleContextImpl(request, response);
        function.invoke(context);
        return response.getResult();
    }

    /**
     * 获取实例的名称
     * @param groupId
     * @param funcNo
     * @return
     */
    private String getFunctionBeanName(String groupId, String funcNo){
        return "func" + funcNo;
    }

    /**
     * 准备请求对象
     * @param groupId
     * @param funcNo
     * @param token
     * @param params
     * @return
     */
    private Request prepareRequest(String groupId, String funcNo, String token, Map params){
        SimpleRequestImpl request = new SimpleRequestImpl();
        request.setFuncNo(funcNo);
        request.setGroupId(groupId);
        Map map = new HashMap();
        map.putAll(params);
        map.put("df4j-token", token);
        request.setParamMap(map);
        return request;
    }
}
