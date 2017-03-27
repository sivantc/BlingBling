package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BusniessLoginRegister extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busniess_login_register);

        Button button_busniess_register = (Button) findViewById(R.id.button_busniess_register);
        button_busniess_register.setOnClickListener(this);


    }

    public void onClick (View v) {
        switch(v.getId()) {
            case R.id.button_busniess_login:
                Intent regActivity = new Intent(this, RegisterActivity.class);
                startActivity(regActivity);
                break;
        }
    }

}
