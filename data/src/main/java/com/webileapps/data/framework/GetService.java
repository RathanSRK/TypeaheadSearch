package com.webileapps.data.framework;

import android.content.Context;

import com.android.volley.Request;
import com.webileapps.data.framework.volley.VolleyClient;
import com.webileapps.data.framework.volley.WAGsonRequest;


public abstract class GetService<T> extends RestService<T> implements IService<T> {

    public GetService(Context context) {
        super(context);
    }

    @Override
    public void sendRequest() {

        WAGsonRequest.Builder requestBuilder = new WAGsonRequest.Builder<T, Void>()
                .setBaseUrl(getBaseUrl())
                .setEndPointUrl(getEndPointUrl())
                .setVolleyCallBack(callBack)
                .setParams(params)
                .setPathVariables(getPathVariables())
                .setMethod(Request.Method.GET);

        if(getResponseClass() != null) {
            requestBuilder.setResponseClass(getResponseClass());
        } else if (getResponseType() != null) {
            requestBuilder.setResponseType(getResponseType());
        }

        mRequest = requestBuilder.build();

        VolleyClient.getInstance(mContext).addToRequestQueue(mRequest);
    }
}
