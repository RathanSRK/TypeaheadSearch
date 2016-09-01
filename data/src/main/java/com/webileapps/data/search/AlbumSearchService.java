package com.webileapps.data.search;

import android.content.Context;

import com.webileapps.data.framework.GetService;
import com.webileapps.data.framework.IService;


public class AlbumSearchService extends GetService<AlbumSearchResponse> implements IAlbumSearchService {

    private String albumName;

    public AlbumSearchService(Context context) {
        super(context);
        params.put("method","album.search");
        params.put("api_key","63eb95272a18420f8565e7cc52328e7c");
        params.put("format","json");
        params.put("limit","150");
    }

    @Override
    protected String getEndPointUrl() {
        return "";
    }

    @Override
    public AlbumSearchService setAlbumName(String albumName) {
        this.albumName=albumName;
        return this;
    }

    @Override
    public IService<AlbumSearchResponse> setParams() {

        params.put("album",albumName);
        return super.setParams();
    }

    @Override
    protected Class<AlbumSearchResponse> getResponseClass() {
        return AlbumSearchResponse.class;
    }
}
//method=album.search&album=a&api_key=63eb95272a18420f8565e7cc52328e7c&format=json&limit=150