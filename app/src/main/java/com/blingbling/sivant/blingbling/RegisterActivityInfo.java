package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Sivan on 26/03/2017.
 */

public class RegisterActivityInfo extends MutualFunc{

    //  System.out.print("starting RegisterActivityInfo");

    private int progress_km  = 50;
    private SeekBar seekBar_distance;
    private TextView textView_km;
    private EditText ed_name;
    private EditText ed_lastname;

    //   private ProgressDialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        Toast.makeText(RegisterActivityInfo.this, "inside regactinfo ", Toast.LENGTH_SHORT).show();


        ed_name          = (EditText) findViewById(R.id.ed_name);
        ed_lastname      = (EditText) findViewById(R.id.ed_lastname);
        seekBar_distance = (SeekBar) findViewById(R.id.seekBar_distance);
        textView_km      = (TextView) findViewById(R.id.textView_km);
        Button bt_select_my_preferences = (Button) findViewById(R.id.button_select_my_preferences);
        setInfoInUtils();
        seek_bar_km(seekBar_distance, 50, "I want to got notification under distance of "," km");
        bt_select_my_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPreferences();
            }
        });
    }
    protected void selectPreferences(){
        System.out.print("starting RegisterActivityInfo");
        Log.d("my activity", "starting RegisterActivityInfo");
        String firstname = ed_name.getText().toString().trim();
        String lastname = ed_lastname.getText().toString().trim();
        int radius      = UtilsBlingBling.getProgressBar();

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(lastname)){

        }
        UserPreferences userPreferences = new UserPreferences(firstname, lastname, radius, FirebaseInstanceId.getInstance().getToken(), -1, -1);
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child("Users").child(udid).setValue(userPreferences);
        Toast.makeText(RegisterActivityInfo.this, "Preferences saved...", Toast.LENGTH_SHORT).show();
        UtilsBlingBling.setNotRegistering(false);
        Intent userHomePage = new Intent(this, UserHomePage.class);
        startActivity(userHomePage);
        //   progress_dialog.setMessage("w User....");
    //    progress_dialog.show();


    }

    @Override
    protected void setInfoInUtils() {
        UtilsBlingBling.setTextViewProgress(textView_km);
        UtilsBlingBling.setProgressBar(progress_km);
    }
}
