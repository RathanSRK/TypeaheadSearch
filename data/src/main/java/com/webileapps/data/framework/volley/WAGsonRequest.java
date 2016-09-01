package com.webileapps.data.framework.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


public class WAGsonRequest<T, P> extends Request<T> {
    protected final static Gson gson = new GsonBuilder().serializeNulls().create();
    private Class<T> clazz;
    private Type type;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private P payload;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    private WAGsonRequest(String url, int method, Class<T> clazz, Map<String, String> headers,
                          Response.Listener<T> listener, Response.ErrorListener errorListener, P payload) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
        this.payload = payload;
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param typeOfT Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    private WAGsonRequest(String url, int method, Type typeOfT, Map<String, String> headers,
                          Response.Listener<T> listener, Response.ErrorListener errorListener, P payload) {
        super(method, url, errorListener);
        this.type = typeOfT;
        this.headers = headers;
        this.listener = listener;
        this.payload = payload;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        if (listener != null)
            listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if(payload!=null) {
            String jsonPayload = gson.toJson(payload);
            return jsonPayload.getBytes();
        }
        return super.getBody();
    }



    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            if (clazz != null)
                return Response.success(
                        gson.fromJson(json, clazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            else
                return (Response<T>) Response.success(
                        gson.fromJson(json, type),
                        HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    public static class Builder<T, P> {

        private Class<T> clazz;
        private Type type;
        private Map<String, String> headers = new HashMap<>();
        private Response.Listener<T> successListener;
        private Response.ErrorListener errorListener;
        private String baseUrl;
        private String endPointUrl;
        private P payload;
        private int method;
        private String params;
        private String[] pathVariables;

        public Builder() {
          //  this.baseUrl = APIConstants.BASE_URL;
            method = Method.GET;
            params = "";
        }

        public Builder<T, P> setResponseClass(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder<T, P> setHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder<T, P> setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder<T, P> setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder<T, P> setResponseType(Type type) {
            this.type = type;
            return this;
        }


        public Builder<T,P> setPathVariables(String... pathVariables) {
            this.pathVariables = pathVariables;
            return this;
        }

        public Builder<T, P> setEndPointUrl(String endPointUrl) {
            this.endPointUrl = endPointUrl;
            return this;
        }

        public Builder<T, P> setVolleyCallBack(final WAVolleyCallBack<T> callBack) {

            if(callBack != null) {

                successListener = new Response.Listener<T>() {
                    @Override
                    public void onResponse(T response) {
                        Log.d("WAGsonRequest","Success: " + gson.toJson(response));
                        callBack.onSuccess(response);
                    }
                };

                errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("WAGsonRequest","Error Message: " + error.getMessage());
                        Log.e("WAGsonRequest","Error Body: " + getErrorBody(error));
                        callBack.onError(error);
                    }
                };

            }

            return this;
        }

        private String getErrorBody(VolleyError volleyError)  {
            try {
                return new String(volleyError.networkResponse.data);
            } catch (NullPointerException ex) {
                //ex.printStackTrace();
            }
            return "{}";
        }

        public Builder<T, P> setPayload(P payload) {
            this.payload = payload;
            if (payload != null)
                method = Method.POST;
            return this;
        }

        public Builder<T, P> setPayload(P payload, int method) {
            this.payload = payload;
            this.method = method;
            return this;
        }

        public WAGsonRequest<T,P> build() {

            headers.put("Content-Type","application/json");

            if (clazz == null && type == null)
                throw new IllegalArgumentException("Response class and type both cannot be null");
            if (endPointUrl == null)
                throw new IllegalArgumentException("End Point Url cannot be null");
            if(pathVariables != null) {
                endPointUrl = String.format(endPointUrl,pathVariables);
            }

            if(clazz != null)
                return new WAGsonRequest<T,P>(baseUrl + endPointUrl + params, method, clazz, headers, successListener, errorListener, payload);
            else
                return new WAGsonRequest<T,P>(baseUrl + endPointUrl + params, method, type, headers, successListener, errorListener, payload);
        }

        public Builder<T,P> setParams(Map<String, String> paramsMap) {

            if(paramsMap != null && paramsMap.size()>0) {
                StringBuilder sb = new StringBuilder();
                sb.append("?");

                for(String key : paramsMap.keySet()) {
                    sb.append(key);
                    sb.append("=");
                    sb.append(paramsMap.get(key));
                    sb.append("&");
                }

                this.params = sb.toString();
                this.params = params.substring(0,params.length()-1);
            }

            return this;
        }
    }

    public interface WAVolleyCallBack<T> {
        void onSuccess(T response);
        void onError(VolleyError volleyError);
    }

}