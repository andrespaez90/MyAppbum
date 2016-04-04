package com.innso.request.Interfaces;

import java.util.Map;

public interface BaseRequest {

    enum HttpMethod {
        POST, GET, PUT, DELETE
    }

    String getMediaType();

    String getUrl();

    HttpMethod getMethod();

    Map<String,String> getHeaders();

    Object getBody();

    Map<String,String> getUrlParams();

    Map<String,String> getFormParams();

}
