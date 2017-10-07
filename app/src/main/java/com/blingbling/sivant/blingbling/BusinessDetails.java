package com.blingbling.sivant.blingbling;

import java.util.ArrayList;

/**
 * Created by Sivan on 30/03/2017.
 */

public class BusinessDetails {

    private String businessName;
    private String businessAddress;
    private String phoneNumber;
    private ArrayList<Integer> selected_business_type_items;
    private MyLocation location;
  //  GeoLocation location;

    public BusinessDetails(){

    }


    public BusinessDetails(String businessName, String businessAddress,
                           String phoneNumber, ArrayList<Integer> selected_business_type_items) {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.phoneNumber = phoneNumber;
        this.selected_business_type_items = selected_business_type_items;
        this.location = UtilsBlingBling.getLocation();

      //  this.location = new GeoLocation(UtilsBlingBling.getLocation().getLatitude(),UtilsBlingBling.getLocation().getLongitude());
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Integer> getSelected_business_type_items() {
        return selected_business_type_items;
    }

    public MyLocation getLocation() {
        return location;
    }
}


