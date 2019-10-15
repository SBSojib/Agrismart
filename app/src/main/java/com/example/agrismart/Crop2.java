package com.example.agrismart;

public class Crop2 {
    String Name = "";
    int Month = 0;
    int Latitude = 0;
    int Longitude = 0;

    public Crop2() {

    }

    public Crop2(String Name,int month, int latitude, int longitude) {
        this.Month = month;
        this.Name=Name;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }


    public String getName() {
        return Name;
    }

    public int getLatitude() {
        return Latitude;
    }

    public int getLongitude() {
        return Longitude;
    }

    public int getMonth() {
        return Month;
    }


}
