


package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class BusniessRegisterActivityInfo extends AppCompatActivity implements View.OnClickListener {
    private final int PICK_PLACE = 1;
    private final int PICK_IMAGE = 2;
    private Button button_pick_busniess_address;
    private EditText ed_busniess_name;
    private EditText ed_busniess_address;
    private EditText ed_phone_number;
    private Button button_select_image;
    private ImageView image_view_choosen_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busniess_register_info);
        ed_busniess_name    = (EditText) findViewById(R.id.ed_busniess_name);
        ed_busniess_address = (EditText) findViewById(R.id.ed_busniess_address);
        ed_phone_number     = (EditText) findViewById(R.id.ed_phone_number);
        button_pick_busniess_address = (Button) findViewById(R.id.button_pick_busniess_address);
        image_view_choosen_image = (ImageView) findViewById(R.id.image_view_choosen_image);
        button_select_image = (Button) findViewById(R.id.button_select_image);
        button_pick_busniess_address.setOnClickListener(this);
        button_select_image.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pick_busniess_address:
                pickBusniessAddress();
                break;
            case R.id.button_select_image:
                selectImage();
                break;
        }
    }


    private void pickBusniessAddress() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, PICK_PLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PLACE && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            displaySelectedImage(data);
        }
        else{
            Log.e("request Error", "didn't find");

        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);
        String busniessName = placeSelected.getName().toString();
        String busniessAddress = placeSelected.getAddress().toString();
        String phoneNumber = placeSelected.getPhoneNumber().toString();
        ed_busniess_name.setText(busniessName);
        ed_busniess_address.setText(busniessAddress);
        ed_phone_number.setText(phoneNumber);
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select an image"), PICK_IMAGE);
    //    startActivityForResult(intent, PICK_IMAGE);
    }

    private void displaySelectedImage(Intent data) {
        Uri filePath = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            image_view_choosen_image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //   image_view_choosen_image

    }
}
