package com.blingbling.sivant.blingbling;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNewCoupon extends MutualFunc implements View.OnClickListener{


    private Button button_select_image;
    private Button button_create_new_coupon;
    private Uri uri_filePath_busienssSpace = null;
    private ImageView image_view_choosen_image;
    private int progress_hours  = 24;
    private SeekBar seekBar_hours;
    private TextView textView_hours;
    private EditText ed_price;
    private EditText ed_description;
    private EditText ed_couponId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_coupon);
        ed_price = (EditText) findViewById(R.id.ed_price);
        ed_description = (EditText) findViewById(R.id.ed_description);
        seekBar_hours = (SeekBar) findViewById(R.id.seekBar_hours);
        textView_hours = (TextView) findViewById(R.id.textView_hours);
        image_view_choosen_image = (ImageView) findViewById(R.id.image_view_coupon_image);
        button_select_image = (Button) findViewById(R.id.button_select_image);
        button_select_image.setOnClickListener(this);
        button_create_new_coupon= (Button) findViewById(R.id.button_create_new_coupon);
        button_create_new_coupon.setOnClickListener(this);
        ed_couponId = (EditText) findViewById(R.id.ed_couponId);
        setInfoInUtils();
        seek_bar_km(seekBar_hours, 24, "This coupon will be available for the next ", "hours");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_select_image:
                selectImage();
                break;
            case R.id.button_create_new_coupon:

                //todo save unique number for each coupon
                uploadFile(ed_couponId.getText().toString().trim());
                addCoupon();
                break;
        }
    }

    protected void addCoupon(){
        Log.d("createNewCoupon", "starting createNewCoupon");

        //price should be double
        String price = ed_price.getText().toString().trim();
        String description = ed_description.getText().toString().trim();
        int hours      = UtilsBlingBling.getProgressBar();
        String couponId = ed_couponId.getText().toString().trim();
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        CouponDetails couponDetails = new CouponDetails(price, description, hours, couponId, udid);
        UtilsBlingBling.getDatabaseReference().child("BusniessUser").child(udid).child("Coupons").child(ed_couponId.getText().toString().trim()).setValue(couponDetails);
        uploadFile(UtilsBlingBling.getCurrentNum());
        Toast.makeText(CreateNewCoupon.this, "Details saved...", Toast.LENGTH_SHORT).show();
    }

        @Override
    protected void setInfoInUtils() {
        UtilsBlingBling.setUri_filePath_busienssSpace(uri_filePath_busienssSpace);
        UtilsBlingBling.setImage_view_choosen_image(image_view_choosen_image);
        UtilsBlingBling.setCurrentContextName(this);
        UtilsBlingBling.setTextViewProgress(textView_hours);
        UtilsBlingBling.setProgressBar(progress_hours);

    }
}
