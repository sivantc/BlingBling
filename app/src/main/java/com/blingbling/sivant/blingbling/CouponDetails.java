package com.blingbling.sivant.blingbling;

import android.text.format.DateUtils;



/**
 * Created by Sivan on 04/04/2017.
 */

public class CouponDetails {

    private String price;
    private String description;
    private String couponId;
    private String busniessId;
    private long timeOver;
    private String couponToken;


    public CouponDetails(String ed_price, String ed_description, int progress_hours, String couponId, String busniessId, String couponToken) {
        this.price = ed_price;
        this.description = ed_description;
        this.timeOver = System.currentTimeMillis() + progress_hours * DateUtils.HOUR_IN_MILLIS;
        this.couponId = couponId;
        this.busniessId = busniessId;
        this.couponToken = couponToken;
    }
    public CouponDetails() {}

    public String getCouponId() {
        return couponId;
    }

    public long getTimeOver() {
        return timeOver;
    }

    public String getBusniessId() {
        return busniessId;
    }
    public String getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }

    public String getCouponToken() {
        return couponToken;
    }
}

