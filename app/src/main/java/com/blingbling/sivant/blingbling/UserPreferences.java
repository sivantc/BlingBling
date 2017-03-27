package com.blingbling.sivant.blingbling;

/**
 * Created by Daniel on 27/03/2017.
 */

public class UserPreferences {
    public String firstname;
    public String lastname;
    public int radios;

    public UserPreferences(){

    }

    public UserPreferences(String firstname, String lastname, int radios) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.radios = radios;
    }
}
