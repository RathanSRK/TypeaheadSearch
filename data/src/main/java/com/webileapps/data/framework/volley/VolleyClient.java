package com.webileapps.data.framework.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyClient {

    private static VolleyClient ourInstance;
    private final Context context;
    private RequestQueue mRequestQueue;

    public static VolleyClient getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new VolleyClient(context);
        return ourInstance;
    }

    private VolleyClient(Context context) {
        this.context = context;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        try {
            Log.d("WAGsonRequest","Volley request initiated: ");
            Log.d("WAGsonRequest","URL : " + req.getUrl());
            Log.d("WAGsonRequest","Request Type : " + getMethod(req));
            Log.d("WAGsonRequest","Request Body: " + getBody(req));

        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        getRequestQueue().add(req);
    }

    private String getMethod(Request request) {
        switch (request.getMethod()) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.DELETE:
                return "DELETE";
            case Request.Method.POST:
                return "POST";
            case Request.Method.PUT:
                return "PUT";
            default:
                return "UNKNOWN TYPE: " + request.getMethod();
        }
    }

    private String getBody(Request req) throws AuthFailureError {
        try {
            return new String(req.getBody());
        } catch (NullPointerException ex) {
           // ex.printStackTrace();
        }
        return "{}";
    }
}
