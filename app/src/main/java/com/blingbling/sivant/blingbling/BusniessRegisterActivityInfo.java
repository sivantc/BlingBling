


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




import java.util.ArrayList;


public class BusniessRegisterActivityInfo extends MutualFunc implements View.OnClickListener {
    private Button button_pick_busniess_address;
    private EditText ed_busniess_name;
    private EditText ed_busniess_address;
    private EditText ed_phone_number;
    private Uri uri_filePath_busienssSpace = null;
    private Button button_select_busniess_type;
    private Button button_select_image;
    private Button button_complete_registration;
    private ImageView image_view_choosen_image;
    private TextView textView_selected_busniess_type;
    private ArrayList<Integer> arrayList_selected_busniess_type_items = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busniess_register_info);
        ed_busniess_name = (EditText) findViewById(R.id.ed_busniess_name);
        ed_busniess_address = (EditText) findViewById(R.id.ed_busniess_address);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        button_pick_busniess_address = (Button) findViewById(R.id.button_pick_busniess_address);
        button_select_busniess_type = (Button) findViewById(R.id.button_select_busniess_type);
        image_view_choosen_image = (ImageView) findViewById(R.id.image_view_choosen_image);
        textView_selected_busniess_type = (TextView) findViewById(R.id.textView_selected_busniess_type);
        button_select_image = (Button) findViewById(R.id.button_select_image);
        button_complete_registration = (Button) findViewById(R.id.button_complete_registration);
        button_pick_busniess_address.setOnClickListener(this);
        button_select_busniess_type.setOnClickListener(this);
        button_select_image.setOnClickListener(this);
        button_complete_registration.setOnClickListener(this);
        setInfoInUtils();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pick_busniess_address:
                pickBusniessAddress(this);
                break;
            case R.id.button_select_image:
                selectImage();
                break;
            case R.id.button_complete_registration:
                uploadFile("0");
                insetrDetails();
                Intent busniessMenuActivity = new Intent(this, BusniessMenu.class);
                startActivity(busniessMenuActivity);
                break;
            case R.id.button_select_busniess_type:
                selectBusniessType();
                break;
        }
    }

    protected void setInfoInUtils() {
        UtilsBlingBling.setCurrentActivity(this);
        UtilsBlingBling.setCurrentContextName(this);
        UtilsBlingBling.setEd_busniess_name(ed_busniess_name);
        UtilsBlingBling.setEd_busniess_address(ed_busniess_address);
        UtilsBlingBling.setEd_phone_number(ed_phone_number);
        UtilsBlingBling.setUri_filePath_busienssSpace(uri_filePath_busienssSpace);
        UtilsBlingBling.setArrayList_selected_busniess_type_items(arrayList_selected_busniess_type_items);
        UtilsBlingBling.setTextView_selected_busniess_type(textView_selected_busniess_type);
        UtilsBlingBling.setImage_view_choosen_image(image_view_choosen_image);
    }


    private void insetrDetails(){
        System.out.print("starting RegisterActivityInfo");
        Log.d("my activity", "starting RegisterActivityInfo");
        String busniessName = ed_busniess_name.getText().toString().trim();
        String busniessAddress = ed_busniess_address.getText().toString().trim();
        String phoneNumber = ed_phone_number.getText().toString().trim();


        if(TextUtils.isEmpty(busniessName)){
            Toast.makeText(this, "Please enter your busniess name", Toast.LENGTH_SHORT).show();
            return;
        }
        BusniessDetails busniessDetails = new BusniessDetails(busniessName, busniessAddress, phoneNumber, arrayList_selected_busniess_type_items);
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child("BusniessUsers").child(udid).setValue(busniessDetails);
        Toast.makeText(BusniessRegisterActivityInfo.this, "Details saved...", Toast.LENGTH_SHORT).show();

    }

}

