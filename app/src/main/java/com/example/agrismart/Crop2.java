package com.example.agrismart;

public class Crop2 {
    String name = "";
    int month = 0;
    int latitude = 0;
    int longitude = 0;

    public Crop2() {

    }

    public Crop2(String Name,int month, int latitude, int longitude) {
        this.month = month;
        this.name=Name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getMonth() {
        return month;
    }


}
