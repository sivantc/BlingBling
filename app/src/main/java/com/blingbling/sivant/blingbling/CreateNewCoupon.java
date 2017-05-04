package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class CreateNewCoupon extends MutualFunc implements View.OnClickListener{


    private Button button_select_image;
    private Uri uri_filePath_busienssSpace = null;
    private ImageView image_view_choosen_image;
    private int progress_km  = 50;
    private SeekBar seekBar_hours;
    private TextView textView_hours;
    private EditText ed_price;
    private EditText ed_description;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_coupon);
        ed_price          = (EditText) findViewById(R.id.ed_price);
        ed_description      = (EditText) findViewById(R.id.ed_description);
        seekBar_hours = (SeekBar) findViewById(R.id.seekBar_hours);
        textView_hours      = (TextView) findViewById(R.id.textView_hours);
        image_view_choosen_image = (ImageView) findViewById(R.id.image_view_choosen_image);
        button_select_image = (Button) findViewById(R.id.button_select_image);
        button_select_image.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_select_image:
                selectImage();
                break;
        }
    }

    @Override
    protected void setInfoInUtils() {
        UtilsBlingBling.setUri_filePath_busienssSpace(uri_filePath_busienssSpace);
        UtilsBlingBling.setImage_view_choosen_image(image_view_choosen_image);
        UtilsBlingBling.setCurrentContextName(this);

    }
}
