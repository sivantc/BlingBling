package com.blingbling.sivant.blingbling;

/**
 * Created by Sivan on 27/03/2017.
 */

public class UserPreferences {

    private String firstname;
    private String lastname;
    private int radius;
    private String deviceToken;
    private double [] userLocation;
    public UserPreferences(){

    }

    public UserPreferences(String firstname, String lastname, int radius, String deviceToken, double[] userLocation) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.radius = radius;
        this.deviceToken = deviceToken;
        this.userLocation = userLocation;
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

    public double[] getUserLocation() {
        return userLocation;
    }
}
