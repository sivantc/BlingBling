


package com.blingbling.sivant.blingbling;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;


public class BusinessRegisterActivityInfo extends MutualFunc implements View.OnClickListener {
    private Button button_pick_business_address;
    private EditText ed_business_name;
    private EditText ed_business_address;
    private EditText ed_phone_number;
    private Uri uri_filePath_busienssSpace = null;
    private Button button_select_business_type;
    private Button button_select_image;
    private Button button_complete_registration;
    private ImageView image_view_choosen_image;
    private TextView textView_selected_business_type;
    private ArrayList<Integer> arrayList_selected_business_type_items = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register_info);
        ed_business_name = (EditText) findViewById(R.id.ed_business_name);
        ed_business_address = (EditText) findViewById(R.id.ed_business_address);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        button_pick_business_address = (Button) findViewById(R.id.button_pick_business_address);
        button_select_business_type = (Button) findViewById(R.id.button_select_business_type);
        image_view_choosen_image = (ImageView) findViewById(R.id.image_view_coupon_image);
        textView_selected_business_type = (TextView) findViewById(R.id.textView_selected_business_type);
        button_select_image = (Button) findViewById(R.id.button_select_image);
        button_complete_registration = (Button) findViewById(R.id.button_complete_registration);
        button_pick_business_address.setOnClickListener(this);
        button_select_business_type.setOnClickListener(this);
        button_select_image.setOnClickListener(this);
        button_complete_registration.setOnClickListener(this);
        setInfoInUtils();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pick_business_address:
                pickBusinessAddress(this);
                break;
            case R.id.button_select_image:
                selectImage();
                break;
            case R.id.button_complete_registration:
                uploadFile("0");
                insetrDetails();
                UtilsBlingBling.setNotRegistering(true);
                Intent businessMenuActivity = new Intent(this, BusinessMenu.class);
                startActivity(businessMenuActivity);
                break;
            case R.id.button_select_business_type:
                selectBusinessType();
                break;
        }
    }

    protected void setInfoInUtils() {
        UtilsBlingBling.setCurrentActivity(this);
        UtilsBlingBling.setCurrentContextName(this);
        UtilsBlingBling.setEd_business_name(ed_business_name);
        UtilsBlingBling.setEd_business_address(ed_business_address);
        UtilsBlingBling.setEd_phone_number(ed_phone_number);
        UtilsBlingBling.setUri_filePath_businessSpace(uri_filePath_busienssSpace);
        UtilsBlingBling.setArrayList_selected_business_type_items(arrayList_selected_business_type_items);
        UtilsBlingBling.setTextView_selected_business_type(textView_selected_business_type);
        UtilsBlingBling.setImage_view_choosen_image(image_view_choosen_image);
    }


    private void insetrDetails(){
        System.out.print("starting RegisterActivityInfo");
        Log.d("my activity", "starting RegisterActivityInfo");
        String businessName = ed_business_name.getText().toString().trim();
        String businessAddress = ed_business_address.getText().toString().trim();
        String phoneNumber = ed_phone_number.getText().toString().trim();


        if(TextUtils.isEmpty(businessName)){
            Toast.makeText(this, "Please enter your business name", Toast.LENGTH_SHORT).show();
            return;
        }
        BusinessDetails businessDetails = new BusinessDetails(businessName, businessAddress, phoneNumber, arrayList_selected_business_type_items);
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child("BusinessUsers").child(udid).setValue(businessDetails);
        GeoFire geoFire = new GeoFire(UtilsBlingBling.getDatabaseReference().child("BusinessLocations"));
        geoFire.setLocation(udid,new GeoLocation(UtilsBlingBling.getLocation().getLatitude(), UtilsBlingBling.getLocation().getLongitude()));
        Toast.makeText(BusinessRegisterActivityInfo.this, "Details saved...", Toast.LENGTH_SHORT).show();


    }

}

