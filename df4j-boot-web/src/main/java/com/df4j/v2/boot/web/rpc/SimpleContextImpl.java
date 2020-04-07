package com.df4j.v2.boot.web.rpc;

import com.df4j.v2.base.rpc.Context;
import com.df4j.v2.base.rpc.Request;
import com.df4j.v2.base.rpc.Response;

public class SimpleContextImpl implements Context {

    private Request request;
    private Response response;

    public SimpleContextImpl(){}

    public SimpleContextImpl(Request request, Response response){
        this.request = request;
        this.response = response;
    }


    @Override
    public Request getRequest() {
        return null;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public Response getResponse() {
        return null;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
