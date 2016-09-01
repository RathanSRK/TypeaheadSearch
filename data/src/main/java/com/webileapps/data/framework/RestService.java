package com.webileapps.data.framework;


import android.content.Context;


import com.webileapps.data.APIConstants;
import com.webileapps.data.framework.volley.WAGsonRequest;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class RestService<T> implements IService<T> {

    protected final Context mContext;
    protected WAGsonRequest.WAVolleyCallBack<T> callBack;
    protected Map<String, String> params;
    public final String ACCESS_TOKEN_KEY = "access_token";
    protected WAGsonRequest mRequest;

    public RestService(Context context) {
        this.mContext = context;
        params = new HashMap<>();
    }

    @Override
    public void setCallBack(WAGsonRequest.WAVolleyCallBack<T> callBack) {
        this.callBack = callBack;
    }


    @Override
    public void cancelRequest() {
        if (mRequest != null) {
            mRequest.cancel();
            mRequest = null;
        }
    }

    @Override
    public IService<T> setParams() {
        return this;
    }

    public String getBaseUrl() {
        return APIConstants.BASE_URL;
    }

    public String[] getPathVariables() {
        return null;
    }

    protected Class<T> getResponseClass() {
        return null;
    }

    protected Type getResponseType() {
        return null;
    }

    protected abstract String getEndPointUrl();
}
