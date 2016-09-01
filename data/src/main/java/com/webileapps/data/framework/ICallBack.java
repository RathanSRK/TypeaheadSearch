package com.webileapps.data.framework;


import com.webileapps.data.framework.volley.WAGsonRequest;

public interface ICallBack<T> {
    void setCallBack(WAGsonRequest.WAVolleyCallBack<T> callBack);
}
