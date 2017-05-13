package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.blingbling.sivant.blingbling.R.id.ed_phone_number;

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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_coupon);
        ed_price = (EditText) findViewById(R.id.ed_price);
        ed_description = (EditText) findViewById(R.id.ed_description);
        seekBar_hours = (SeekBar) findViewById(R.id.seekBar_hours);
        textView_hours = (TextView) findViewById(R.id.textView_hours);
        image_view_choosen_image = (ImageView) findViewById(R.id.image_view_choosen_image);
        button_select_image = (Button) findViewById(R.id.button_select_image);
        button_select_image.setOnClickListener(this);
        button_create_new_coupon= (Button) findViewById(R.id.button_create_new_coupon);
        button_create_new_coupon.setOnClickListener(this);
        setInfoInUtils();
        seek_bar_km(seekBar_hours, 24);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_select_image:
                selectImage();
                break;
            case R.id.button_create_new_coupon:
                addCoupon();
                break;
        }
    }

    protected void addCoupon(){
        Log.d("createNewCoupon", "starting createNewCoupon");
        String price = ed_price.toString().trim();
        String description = ed_description.toString().trim();
        int hours      = UtilsBlingBling.getProgressBar();

        CouponDetails couponDetails = new CouponDetails(price, description, hours);
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child(udid).setValue(couponDetails);
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
