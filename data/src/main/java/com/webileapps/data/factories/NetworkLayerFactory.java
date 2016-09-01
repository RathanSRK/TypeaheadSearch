package com.webileapps.data.factories;

import android.content.Context;

import com.webileapps.data.search.AlbumSearchService;
import com.webileapps.data.search.IAlbumSearchService;


public class NetworkLayerFactory implements INetworkLayerFactory {

    private final Context context;
    private IAlbumSearchService albumSearchService;

    public NetworkLayerFactory(Context context) {
        this.context = context;
    }


    private void throwContextErrorIfNull() {
        if (context == null)
            throw new IllegalArgumentException("Context cannot be null");
    }

    @Override
    public IAlbumSearchService getAlbumSearchService() {
        throwContextErrorIfNull();
        if (albumSearchService==null){
            albumSearchService=new AlbumSearchService(context);
        }
        return albumSearchService;
    }
}
