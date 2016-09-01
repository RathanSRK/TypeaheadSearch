package com.webileapps.data.framework;


public interface IPostService<P, T> extends IService<T> {
    PostService<P, T> setPayload(P payload);
}
