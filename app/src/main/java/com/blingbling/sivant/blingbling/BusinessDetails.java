package com.blingbling.sivant.blingbling;

import android.location.Location;

import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sivan on 30/03/2017.
 */

public class BusinessDetails {

    private String busniessName;
    private String busniessAddress;
    private String phoneNumber;
    private ArrayList<Integer> selected_busniess_type_items;
    private MyLocation location;
  //  GeoLocation location;

    public BusinessDetails(){

    }


    public BusinessDetails(String busniessName, String busniessAddress,
                           String phoneNumber, ArrayList<Integer> selected_busniess_type_items) {
        this.busniessName = busniessName;
        this.busniessAddress = busniessAddress;
        this.phoneNumber = phoneNumber;
        this.selected_busniess_type_items = selected_busniess_type_items;
        this.location = UtilsBlingBling.getLocation();

      //  this.location = new GeoLocation(UtilsBlingBling.getLocation().getLatitude(),UtilsBlingBling.getLocation().getLongitude());
    }

    public String getBusniessName() {
        return busniessName;
    }

    public String getBusniessAddress() {
        return busniessAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Integer> getSelected_busniess_type_items() {
        return selected_busniess_type_items;
    }

    public MyLocation getLocation() {
        return location;
    }
}

