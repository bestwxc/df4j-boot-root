package com.df4j.v2.boot.web.rpc;

import com.df4j.v2.base.rpc.Response;
import com.df4j.v2.base.rpc.Result;

public class SimpleResponseImpl implements Response {

    private int errorNo = 0;

    private String errorInfo;

    private Result result;

    public SimpleResponseImpl(){}

    @Override
    public void setErrorNo(int i) {
        this.errorNo = i;
    }

    public int getErrorNo() {
        return errorNo;
    }

    @Override
    public void setErrorInfo(String s) {
        this.errorInfo = s;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    @Override
    public void setResult(Object o) {
        this.result = (Result) o;
    }

    public Result getResult() {
        return result;
    }
}
