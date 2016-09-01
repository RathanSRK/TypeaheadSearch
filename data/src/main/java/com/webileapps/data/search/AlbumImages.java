package com.webileapps.data.search;

import com.google.gson.annotations.SerializedName;


public class AlbumImages {

    @SerializedName("#text")
    String imageUrl;

    String size;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
