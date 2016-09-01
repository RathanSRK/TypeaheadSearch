package com.webileapps.searchexample.activities;

import android.app.Application;

import com.webileapps.data.factories.DataLayerFactory;
import com.webileapps.data.factories.NetworkLayerFactory;

public class TypeaheadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLayerFactory.setNetworkLayerFactory(new NetworkLayerFactory(getApplicationContext()));
    }
}
