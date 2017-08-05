package com.blingbling.sivant.blingbling;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 04/04/2017.
 */

public class CouponDetails {
    public String getEd_price() {
        return ed_price;
    }

    public String getEd_description() {
        return ed_description;
    }

    private String ed_price;
    private String ed_description;

//    public Calendar getTimeOver() {
  //      return timeOver;
//    }

    public Date getTimeOver() {
        return timeOver;
    }

    //    private Calendar timeOver = Calendar.getInstance();
    private Date timeOver = Calendar.getInstance().getTime();


    public CouponDetails(String ed_price, String ed_description, int progress_hours) {
        this.ed_price = ed_price;
        this.ed_description = ed_description;
        timeOver = new Date(timeOver.getTime() + progress_hours * DateUtils.HOUR_IN_MILLIS);
    }
}

