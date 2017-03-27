package com.blingbling.sivant.blingbling;

import android.app.ProgressDialog;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import static java.lang.System.out;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Sivan on 26/03/2017.
 */

public class RegisterActivityInfo extends AppCompatActivity{

    //  System.out.print("starting RegisterActivityInfo");

    private int progress_km = 50;
    private SeekBar seekBar_distance;
    private TextView textView_km;
    private EditText ed_name;
    private EditText ed_lastname;

    //   private ProgressDialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);

        ed_name          = (EditText) findViewById(R.id.ed_name);
        ed_lastname      = (EditText) findViewById(R.id.ed_lastname);
        seekBar_distance = (SeekBar) findViewById(R.id.seekBar_distance);
        textView_km      = (TextView) findViewById(R.id.textView_km);
        Button bt_select_my_preferences = (Button) findViewById(R.id.button_select_my_preferences);
   //     progress_dialog = new ProgressDialog(this);
        seek_bar_km();
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
        int radios      = progress_km;

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(lastname)){

        }

        UserPreferences userPreferences = new UserPreferences(firstname, lastname, radios);
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child(udid).setValue(userPreferences);
        Toast.makeText(RegisterActivityInfo.this, "Preferences saved...", Toast.LENGTH_SHORT).show();

        //   progress_dialog.setMessage("w User....");
    //    progress_dialog.show();

    }
    protected void seek_bar_km() {

        // seekBar_distance = (SeekBar) findViewById(R.id.seekBar_distance);
        //  textView_km = (TextView) findViewById(R.id.textView_km);
        seekBar_distance.setMax(50);
        seekBar_distance.setProgress(progress_km);
        seekBar_distance.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_km = progress;
                        textView_km.setText("I want to got notification under distance of " + progress + " km");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }
}
