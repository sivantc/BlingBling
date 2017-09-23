package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BusinessMenu extends AppCompatActivity implements View.OnClickListener{


    private Button button_create_new_coupon;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busniess_menu);
        button_create_new_coupon = (Button) findViewById(R.id.button_create_new_coupon);
        button_create_new_coupon.setOnClickListener(this);
        Button signOut = (Button) findViewById(R.id.signOut);

        mAuth = FirebaseAuth.getInstance();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("signing out....");
                mAuth.signOut();
                //finish();
                startActivity(new Intent(BusinessMenu.this, MainActivity.class));
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_create_new_coupon:
                Intent createNewCouponActivity = new Intent(this, CreateNewCoupon.class);
                startActivity(createNewCouponActivity);
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
