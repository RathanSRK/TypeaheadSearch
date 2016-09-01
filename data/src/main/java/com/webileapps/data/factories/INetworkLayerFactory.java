package com.webileapps.data.factories;

import com.webileapps.data.search.IAlbumSearchService;

public interface INetworkLayerFactory {
    IAlbumSearchService getAlbumSearchService();
}
