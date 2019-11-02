package com.example.agrismart;

public class CropPdf {
    String name;
    String url;

    public CropPdf() {
    }

    public CropPdf(String name,String url) {
        this.name = name;
        this.url=url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
