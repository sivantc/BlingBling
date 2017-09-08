package com.blingbling.sivant.blingbling;

/**
 * Created by Sivan on 27/03/2017.
 */

public class UserPreferences {

    private String firstname;
    private String lastname;
    private int radius;
    private String deviceToken;
    private double latitude;
    private double longitude;
    public UserPreferences(){

    }

    public UserPreferences(String firstname, String lastname, int radius, String deviceToken, double latitude, double longitude) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.radius = radius;
        this.deviceToken = deviceToken;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
