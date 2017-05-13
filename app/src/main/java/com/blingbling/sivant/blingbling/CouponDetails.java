package com.blingbling.sivant.blingbling;

/**
 * Created by Daniel on 04/04/2017.
 */

public class CouponDetails {
    private String ed_price;
    private String ed_description;
    private int progress_hours;

    public CouponDetails(String ed_price, String ed_description, int progress_hours) {
        this.ed_price = ed_price;
        this.ed_description = ed_description;
        this.progress_hours = progress_hours;
    }
}
