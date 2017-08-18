package com.blingbling.sivant.blingbling;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by sivan on 18/08/2017.
 */

public class CouponItemList {


    private ImageView image_view_coupon_image;
    private EditText ed_price;
    private EditText ed_description;
    private EditText textView_relevantTimeText;
    private Button button_more_details;

    public CouponItemList(ImageView image_view_coupon_image, EditText ed_price, EditText ed_description, EditText textView_relevantTimeText, Button button_more_details) {
        this.image_view_coupon_image = image_view_coupon_image;
        this.ed_price = ed_price;
        this.ed_description = ed_description;
        this.textView_relevantTimeText = textView_relevantTimeText;
        this.button_more_details = button_more_details;
    }

    public ImageView getImage_view_coupon_image() {
        return image_view_coupon_image;
    }

    public EditText getEd_price() {
        return ed_price;
    }

    public EditText getEd_description() {
        return ed_description;
    }

    public EditText getTextView_relevantTimeText() {
        return textView_relevantTimeText;
    }

    public Button getButton_more_details() {
        return button_more_details;
    }




}
