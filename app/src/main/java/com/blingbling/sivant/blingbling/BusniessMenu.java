package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BusniessMenu extends AppCompatActivity implements View.OnClickListener{


    private Button button_create_new_coupon;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busniess_menu);
        button_create_new_coupon = (Button) findViewById(R.id.button_create_new_coupon);
        button_create_new_coupon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create_new_coupon:
                Intent createNewCouponActivity = new Intent(this, CreateNewCoupon.class);
                startActivity(createNewCouponActivity);
        }
    }
}
