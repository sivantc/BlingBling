package com.blingbling.sivant.blingbling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText ed_password;
    private EditText ed_email;
    private ProgressDialog progress_dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_password      = (EditText) findViewById(R.id.ed_password);
        ed_email         = (EditText) findViewById(R.id.ed_email);
        Button  button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);
        Button  button_business = (Button) findViewById(R.id.button_business_register);
        button_business.setOnClickListener(this);
        progress_dialog = new ProgressDialog(this);
    }

        public void onClick (View v) {
            switch(v.getId()) {
                case R.id.button_register:
                    UtilsBlingBling.setCurrentlyBusniess(false);
                    register();
                    break;
                case R.id.button_business_register:
                    UtilsBlingBling.setCurrentlyBusniess(true);
                    register();
                    break;
            }
        }

    private void register(){
        final String password = ed_password.getText().toString().trim();
        final String email    = ed_email.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;

        }
        progress_dialog.setMessage("Registering User....");
        progress_dialog.show();
        UtilsBlingBling.getFirebaseAute().createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progress_dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Registeration Succeed!", Toast.LENGTH_SHORT).show();
                    Intent regActivityInfo;
                    if(!UtilsBlingBling.isCurrentlyBusniess())
                         regActivityInfo = new Intent(MainActivity.this, RegisterActivityInfo.class);
                    else
                        regActivityInfo = new Intent(MainActivity.this, BusniessRegisterActivityInfo.class);
                    startActivity(regActivityInfo);
                }
                else {
                    progress_dialog.dismiss();
                    Log.e("Signup Error", "onCancelled", task.getException());
                    System.out.print(task.getException());
                    Toast.makeText(MainActivity.this, "Registeration fail!" + task.getException() + "please try again", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    }


