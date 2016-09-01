package com.webileapps.data.search;

import com.webileapps.data.framework.IService;

public interface IAlbumSearchService extends IService<AlbumSearchResponse>{


    AlbumSearchService setAlbumName(String albumName);
}
