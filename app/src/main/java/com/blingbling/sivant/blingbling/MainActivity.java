package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);


    }

        public void onClick (View v) {
            switch(v.getId()) {
                case R.id.button_register:
                    Intent regActivity = new Intent(this, RegisterActivity.class);
                    startActivity(regActivity);
                    break;
            }
        }


    }

