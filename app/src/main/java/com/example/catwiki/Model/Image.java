package com.example.catwiki.Model;

public class Image {
    private String url;

    public Image(String id, String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
