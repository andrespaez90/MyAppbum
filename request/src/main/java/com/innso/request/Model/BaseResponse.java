package com.innso.request.model;


import java.util.Map;

public class BaseResponse {

    public final static int UNAUTHORIZED = 401;
    public final static int OK = 200;

    public BaseResponse(String bodyResponse, int status) {
    }

    public BaseResponse(Map<String, String> headers, String responseBody, int statusCode) {

    }
}
