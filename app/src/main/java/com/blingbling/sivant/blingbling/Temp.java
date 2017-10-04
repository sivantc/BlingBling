package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class Temp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Button button_signout = (Button) findViewById(R.id.button_signout);
        Button bcoupon = (Button) findViewById(R.id.bcoupon);

        mAuth = FirebaseAuth.getInstance();
        button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("signing out....");
                mAuth.signOut();
                //finish();
                startActivity(new Intent(Temp.this, MainActivity.class));
            }
        });

        bcoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("go to buisness page....");
                startActivity(new Intent(Temp.this, BusinessPage.class));
            }
        });

    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
