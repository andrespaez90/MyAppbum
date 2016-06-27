package com.innso.request;

import android.net.Uri;

import com.innso.request.interfaces.BaseRequest;
import com.innso.request.dispatcher.OkHttpRequestDispatcher;
import com.innso.request.model.MultipartRequest;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class OkHttpBuilder extends Dispatcher {

    @Override
    protected void put(BaseRequest request) {
        com.squareup.okhttp.Request.Builder requestBuilder = createRequest(request);
        RequestBody requestBody = getBody(request);
        if (requestBody != null) {
            requestBuilder.put(requestBody);
        }
        send(requestBuilder.build(), request);
    }

    @Override
    protected void post(BaseRequest request) {
        com.squareup.okhttp.Request.Builder requestBuilder = createRequest(request);
        RequestBody requestBody = getBody(request);
        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }
        send(requestBuilder.build(), request);
    }

    @Override
    protected void delete(BaseRequest request) {
        com.squareup.okhttp.Request.Builder requestBuilder = createRequest(request);
        RequestBody requestBody = getBody(request);
        if (requestBody != null) {
            requestBuilder.delete(requestBody);
        }
        send(requestBuilder.build(), request);
    }

    @Override
    protected void get(BaseRequest request) {
        com.squareup.okhttp.Request.Builder requestBuilder = createRequest(request);
        send(requestBuilder.build(), request);
    }


    private RequestBody getBody(BaseRequest request) {
        if (request.getBody() != null && request.getBody() instanceof String) {

            return RequestBody.create(MediaType.parse(request.getMediaType()), (String) request.getBody());

        } else if (request instanceof MultipartRequest) {

            return getMultipartBody((MultipartRequest) request);

        } else if (request.getFormParams() != null) {

            return getFormBody(request);
        }
        return null;
    }

    public RequestBody getMultipartBody(MultipartRequest request) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        ArrayList<MultipartRequest.MultipartParam> params = request.getMultipartParams();
        if (params != null) {
            for (MultipartRequest.MultipartParam param : params) {
                String contentType = param.getMediaType();
                if (contentType == null) {
                    builder.addFormDataPart(param.getKey(), param.getValue());
                } else {
                    RequestBody file = RequestBody.create(MediaType.parse(contentType), param.getFile());
                    builder.addFormDataPart(param.getKey(), param.getValue(), file);
                }

            }
        }
        return builder.build();
    }


    private RequestBody getFormBody(BaseRequest request) {
        FormEncodingBuilder formBody = new FormEncodingBuilder();
        Map<String, String> params = request.getFormParams();
        if (params != null) {
            Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> thisEntry = (Map.Entry) entries.next();
                formBody.add(thisEntry.getKey(), thisEntry.getValue());
            }
        }
        return formBody.build();
    }

    private com.squareup.okhttp.Request.Builder createRequest(BaseRequest request) {
        com.squareup.okhttp.Request.Builder requestBuilder = new com.squareup.okhttp.Request.Builder();
        requestBuilder.url(getURLFromRequest(request));
        addHeaders(requestBuilder, request);
        return requestBuilder;
    }

    private void addHeaders(com.squareup.okhttp.Request.Builder requestBuilder, BaseRequest request) {
        Map<String, String> params = request.getHeaders();
        if (params != null) {
            Iterator<Map.Entry<String, String>> entries = request.getHeaders().entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> thisEntry = (Map.Entry) entries.next();
                requestBuilder.addHeader(thisEntry.getKey(), thisEntry.getValue());
            }
        }
    }

    private String getURLFromRequest(BaseRequest request) {
        String url = request.getUrl();
        if (request.getMethod() == BaseRequest.HttpMethod.GET) {
            Map<String, String> params = request.getUrlParams();
            if (params != null)
                url = buildUrl(url, params);
        }
        return url;
    }

    private String buildUrl(String url, Map<String, String> params) {
        Uri.Builder builder = Uri.parse(url).buildUpon();
        return appendQueryParameters(builder, params).toString();

    }

    private Uri.Builder appendQueryParameters(Uri.Builder builder, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder;
    }


    private void send(com.squareup.okhttp.Request okHttpRequest, BaseRequest request) {
        OkHttpRequestDispatcher requestDispatcher = new OkHttpRequestDispatcher(mContext, request);
        requestDispatcher.send(okHttpRequest);
    }

}