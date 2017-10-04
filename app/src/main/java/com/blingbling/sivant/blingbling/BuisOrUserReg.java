package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class BuisOrUserReg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buis_or_user_reg);
        Button user = (Button) findViewById(R.id.user);
        Button Buisness = (Button) findViewById(R.id.Buisness);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regActivityInfo;
                regActivityInfo = new Intent(BuisOrUserReg.this, RegisterActivityInfo.class);
                startActivity(regActivityInfo);
            }
        });

        Buisness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regActivityInfo;
                regActivityInfo = new Intent(BuisOrUserReg.this, BusinessRegisterActivityInfo.class);
                startActivity(regActivityInfo);
            }
        });
    }



}
