package com.example.agrismart;

import com.github.barteksc.pdfviewer.PDFView;

public class Crop2 {
    String name = "";
    int month = 0;
    int latitude = 0;
    int longitude = 0;
    double minTemp = 0;
    double maxTemp = 0;
    double minHumidity = 0;
    double maxHumidity = 0;
    int fertilizingInterval = 0;
    int totalFertilizationTime = 0;
    int insecticideInterval = 0;
    int totalInsecticideTime = 0;
    int cultivationDuration = 0;

    PDFView fertilizingPDF;
    PDFView InsecticidePDF;
    PDFView marketPricPDF;
    PDFView newTechnologyPDF;
    PDFView progressPDF;

    public Crop2() {

    }

    public Crop2(String name, int month, int latitude, int longitude, double minTemp, double maxTemp,
                 double minHumidity, double maxHumidity, int fertilizingInterval, int totalFertilizationTime,
                 int insecticideInterval, int totalInsecticideTime, int cultivationDuration, PDFView fertilizingPDF,
                 PDFView insecticidePDF, PDFView marketPricPDF, PDFView newTechnologyPDF, PDFView progressPDF) {
        this.name = name;
        this.month = month;
        this.latitude = latitude;
        this.longitude = longitude;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.fertilizingInterval = fertilizingInterval;
        this.totalFertilizationTime = totalFertilizationTime;
        this.insecticideInterval = insecticideInterval;
        this.totalInsecticideTime = totalInsecticideTime;
        this.cultivationDuration = cultivationDuration;
        this.fertilizingPDF = fertilizingPDF;
        InsecticidePDF = insecticidePDF;
        this.marketPricPDF = marketPricPDF;
        this.newTechnologyPDF = newTechnologyPDF;
        this.progressPDF = progressPDF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(double minHumidity) {
        this.minHumidity = minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(double maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public int getFertilizingInterval() {
        return fertilizingInterval;
    }

    public void setFertilizingInterval(int fertilizingInterval) {
        this.fertilizingInterval = fertilizingInterval;
    }

    public int getTotalFertilizationTime() {
        return totalFertilizationTime;
    }

    public void setTotalFertilizationTime(int totalFertilizationTime) {
        this.totalFertilizationTime = totalFertilizationTime;
    }

    public int getInsecticideInterval() {
        return insecticideInterval;
    }

    public void setInsecticideInterval(int insecticideInterval) {
        this.insecticideInterval = insecticideInterval;
    }

    public int getTotalInsecticideTime() {
        return totalInsecticideTime;
    }

    public void setTotalInsecticideTime(int totalInsecticideTime) {
        this.totalInsecticideTime = totalInsecticideTime;
    }

    public int getCultivationDuration() {
        return cultivationDuration;
    }

    public void setCultivationDuration(int cultivationDuration) {
        this.cultivationDuration = cultivationDuration;
    }

    public PDFView getFertilizingPDF() {
        return fertilizingPDF;
    }

    public void setFertilizingPDF(PDFView fertilizingPDF) {
        this.fertilizingPDF = fertilizingPDF;
    }

    public PDFView getInsecticidePDF() {
        return InsecticidePDF;
    }

    public void setInsecticidePDF(PDFView insecticidePDF) {
        InsecticidePDF = insecticidePDF;
    }

    public PDFView getMarketPricPDF() {
        return marketPricPDF;
    }

    public void setMarketPricPDF(PDFView marketPricPDF) {
        this.marketPricPDF = marketPricPDF;
    }

    public PDFView getNewTechnologyPDF() {
        return newTechnologyPDF;
    }

    public void setNewTechnologyPDF(PDFView newTechnologyPDF) {
        this.newTechnologyPDF = newTechnologyPDF;
    }

    public PDFView getProgressPDF() {
        return progressPDF;
    }

    public void setProgressPDF(PDFView progressPDF) {
        this.progressPDF = progressPDF;
    }
}
