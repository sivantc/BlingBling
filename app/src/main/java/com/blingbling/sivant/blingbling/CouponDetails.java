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
    private String couponCode;
    private String num_of_coupons;



    public CouponDetails(String ed_price, String ed_description, int progress_hours,
                         String couponId, String businessId, String couponToken, String num_of_coupons) {
        this.price = ed_price + "$";
        this.description = ed_description;
        this.startTime = System.currentTimeMillis();
        this.timeOver = System.currentTimeMillis() + progress_hours * DateUtils.HOUR_IN_MILLIS;
        this.couponId = couponId;
        this.businessId = businessId;
        this.couponToken = couponToken;
        this.couponCode = "0";
        this.num_of_coupons = num_of_coupons;
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public void setNum_of_coupons(String num){
        num_of_coupons = num;
    }
    public boolean purchaseCoupon(){
        int temp = Integer.parseInt(num_of_coupons);
        if(temp>0) {
            temp--;
            num_of_coupons = String.valueOf(temp);
            return true;
        }
        else{
            return false;
        }
    }
    public String getNum_of_coupons(){
        return num_of_coupons;
    }


}

