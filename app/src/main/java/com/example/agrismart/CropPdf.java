package com.example.agrismart;

public class CropPdf {
    String name;
    String data;

    public CropPdf() {
    }

    public CropPdf(String name,String data) {
        this.name = name;
        this.data=data;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }
}
