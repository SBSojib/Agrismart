package com.example.agrismart;

public class User {
    String name="";
    String pass="";
    String stat="";

    public User(){
    }

    public User(String name, String pass, String stat) {
        this.name = name;
        this.pass=pass;
        this.stat=stat;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getStat() {
        return stat;
    }
}
