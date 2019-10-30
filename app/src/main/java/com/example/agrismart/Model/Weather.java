package com.example.agrismart.Model;

public class Weather {

    private int it;
    private String main;
    private String description;
    private String icon;

    public Weather(int it, String main, String description, String icon) {
        this.it = it;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public int getIt() {
        return it;
    }

    public void setIt(int it) {
        this.it = it;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
