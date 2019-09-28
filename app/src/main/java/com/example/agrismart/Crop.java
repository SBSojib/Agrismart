package com.example.agrismart;

public class Crop {
    String name="";
    String dateDay="";
    String dateMonth="";
    String dateYear="";
    String quantity="";
    String quantityUnit="";


    public Crop(){

    }
    public Crop(String name,String dateDay,String dateMonth,String dateYear,String quantity,String quantityUnit) {
        this.name = name;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
    }

    public String getName() {
        return name;
    }

    public String getDateDay() {
        return dateDay;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public String getDateYear() {
        return dateYear;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }
}
