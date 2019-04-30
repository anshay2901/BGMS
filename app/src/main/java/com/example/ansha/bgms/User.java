package com.example.ansha.bgms;

import com.google.firebase.database.GenericTypeIndicator;

public class User {
    String name;
    String dob;
    String email;

    public User() {

    }


    public User(String name, String email, String dob) {
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
