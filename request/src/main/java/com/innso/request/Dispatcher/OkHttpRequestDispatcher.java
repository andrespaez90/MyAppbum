package com.innso.request.dispatcher;

import android.content.Context;
import android.os.Handler;

import com.innso.request.Interfaces.BaseRequest;
import com.innso.request.model.BaseResponse;
import com.innso.request.model.OkHttpFactory;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OkHttpRequestDispatcher {

    private BaseRequest request;

    private Context mContext;

    public OkHttpRequestDispatcher(Context context, BaseRequest request) {
        this.request = request;
        this.mContext = context;
    }

    public void send(Request requests) {
        OkHttpClient client = OkHttpFactory.getUnsafeOkHttpClient();

        client.newCall(requests).enqueue(getCallBack());
    }

    public Callback getCallBack() {

        return new Callback() {

            Handler mainHandler = new Handler(mContext.getMainLooper());

            @Override
            public void onFailure(final com.squareup.okhttp.Request request, final IOException e) {

                final String bodyResponse = request.body().toString();

                mainHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        handleError(bodyResponse, e);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String bodyResponse = response.body().string();
                mainHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        onSuccess(bodyResponse);
                    }
                });
            }
        };
    }

    private void onSuccess(String bodyResponse) {
        BaseResponse response = new BaseResponse(bodyResponse, BaseResponse.OK);
    }

    private void handleError(String responseError, IOException exception) {

        String responseBody = "empty";

        int statusCode = 0;

        Map<String, String> headers = null;

        BaseResponse response;

        try {
            responseBody = responseError;

            statusCode = exception.hashCode();

            headers = new HashMap<>();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response = new BaseResponse(responseBody, statusCode);

    }


}