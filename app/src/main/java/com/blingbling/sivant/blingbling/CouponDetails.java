package com.blingbling.sivant.blingbling;

import android.text.format.DateUtils;



/**
 * Created by Sivan on 04/04/2017.
 */

public class CouponDetails {

    private String price;
    private String description;
    private String couponId;
    private String businessId;
    private long timeOver;
    private String couponToken;
    private long startTime;
    private int couponCode;



    public CouponDetails(String ed_price, String ed_description, int progress_hours, String couponId, String businessId, String couponToken) {
        this.price = ed_price + "$";
        this.description = ed_description;
        this.startTime = System.currentTimeMillis();
        this.timeOver = System.currentTimeMillis() + progress_hours * DateUtils.HOUR_IN_MILLIS;
        this.couponId = couponId;
        this.businessId = businessId;
        this.couponToken = couponToken;
        this.couponCode = 0;
    }
    public CouponDetails() {}

    public String getCouponId() {
        return couponId;
    }

    public long getTimeOver() {
        return timeOver;
    }

    public String getBusinessId() {
        return businessId;
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


    public long getStartTime() {
        return startTime;
    }

    public int getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(int couponCode) {
        this.couponCode = couponCode;
    }


}

