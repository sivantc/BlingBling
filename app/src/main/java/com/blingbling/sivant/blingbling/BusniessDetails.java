package com.blingbling.sivant.blingbling;

import java.util.ArrayList;

/**
 * Created by Daniel on 30/03/2017.
 */

public class BusniessDetails {

    private String busniessName;
    private String busniessAddress;
    private String phoneNumber;
    private ArrayList<Integer> selected_busniess_type_items;



    public BusniessDetails(String busniessName, String busniessAddress, String phoneNumber, ArrayList<Integer> selected_busniess_type_items) {
        this.busniessName = busniessName;
        this.busniessAddress = busniessAddress;
        this.phoneNumber = phoneNumber;
        this.selected_busniess_type_items = selected_busniess_type_items;
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
}


