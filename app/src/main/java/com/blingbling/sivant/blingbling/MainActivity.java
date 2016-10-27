package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);


    }

        public void onClick (View v) {
            switch(v.getId()) {
                case R.id.button_register:
                    Intent try1 = new Intent(this, RegisterActivity.class);
                    startActivity(try1);
                    break;
            }
        }


    }

