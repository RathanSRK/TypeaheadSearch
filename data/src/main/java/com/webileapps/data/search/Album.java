package com.webileapps.data.search;

import java.util.List;

public class Album {

    String name;

    String artist;

    String url;

    List<AlbumImages> image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AlbumImages> getImage() {
        return image;
    }

    public void setImage(List<AlbumImages> image) {
        this.image = image;
    }
}
