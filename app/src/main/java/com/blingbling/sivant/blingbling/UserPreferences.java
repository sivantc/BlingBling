package com.blingbling.sivant.blingbling;

/**
 * Created by Sivan on 27/03/2017.
 */

public class UserPreferences {

    private String firstname;
    private String lastname;
    private int radius;

    public UserPreferences(){

    }

    public UserPreferences(String firstname, String lastname, int radius) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.radius = radius;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getRadius() {
        return radius;
    }

}
