package com.webileapps.data.framework;

import android.content.Context;

import com.android.volley.Request;
import com.webileapps.data.framework.volley.VolleyClient;
import com.webileapps.data.framework.volley.WAGsonRequest;


public abstract class PostService<P,T> extends RestService<T> implements IPostService<P,T> {

    private P payload;

    public PostService(Context context) {
        super(context);
    }

    @Override
    public void sendRequest() {

        WAGsonRequest.Builder<T,P> requestBuilder = new WAGsonRequest.Builder<T, P>()
                .setBaseUrl(getBaseUrl())
                .setEndPointUrl(getEndPointUrl())
                .setVolleyCallBack(callBack)
                .setMethod(Request.Method.POST)
                .setParams(params)
                .setPathVariables(getPathVariables())
                .setPayload(payload);

        if(getClass() != null) {
            requestBuilder.setResponseClass(getResponseClass());
        } else if (getResponseType() != null) {
            requestBuilder.setResponseType(getResponseType());
        }

        mRequest = requestBuilder.build();

        VolleyClient.getInstance(mContext).addToRequestQueue(mRequest);
    }

    @Override
    public PostService<P, T> setPayload(P payload) {
        this.payload = payload;
        return this;
    }

}
