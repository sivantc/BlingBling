package com.blingbling.sivant.blingbling;

import android.location.Location;

/**
 * Created by Noy on 21-Aug-17.
 */

public class MyLocation extends Location {
    public MyLocation(String provider) {
        super(provider);
    }

    public MyLocation(){
        super("test");

    }
}
