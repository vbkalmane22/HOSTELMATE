package com.example.sahayadriapp;

public class storingdata {
    String name,usn,password;

    public storingdata(String name, String usn, String password) {
        this.name = name;
        this.usn = usn;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
