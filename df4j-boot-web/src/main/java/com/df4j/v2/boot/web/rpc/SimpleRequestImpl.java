package com.df4j.v2.boot.web.rpc;

import com.df4j.v2.base.rpc.Request;
import java.util.Map;

public class SimpleRequestImpl implements Request {

    private String groupId;

    private String funcNo;

    private Map paramMap;

    public SimpleRequestImpl(){}

    @Override
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String getFuncNo() {
        return funcNo;
    }

    public void setFuncNo(String funcNo) {
        this.funcNo = funcNo;
    }

    @Override
    public Map getRequestMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }
}
