package com.webileapps.data.framework;


import com.webileapps.data.framework.volley.WAGsonRequest;


public interface IService<T> {
    void setCallBack(WAGsonRequest.WAVolleyCallBack<T> callBack);
    void sendRequest();
    void cancelRequest();
    IService<T> setParams();
}
