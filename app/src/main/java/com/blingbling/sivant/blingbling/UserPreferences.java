package com.blingbling.sivant.blingbling;

/**
 * Created by Sivan on 27/03/2017.
 */

public class UserPreferences {

    private String firstname;
    private String lastname;
    private int radius;
    private String deviceToken;

    public UserPreferences(){

    }

    public UserPreferences(String firstname, String lastname, int radius, String deviceToken) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.radius = radius;
        this.deviceToken = deviceToken;
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

    public String getDeviceToken() {
        return deviceToken;
    }
}
