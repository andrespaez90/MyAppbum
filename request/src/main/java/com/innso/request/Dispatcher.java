package com.innso.request;

import android.content.Context;

import com.innso.request.Interfaces.BaseRequest;

public abstract class Dispatcher {

    protected Context mContext;

    protected abstract void post(BaseRequest request);

    protected abstract void get(BaseRequest request);

    protected abstract void put(BaseRequest request);

    protected abstract void delete(BaseRequest request);
}
