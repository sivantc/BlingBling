package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import static android.R.attr.label;

public class BusinessPage extends UserHomePage{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mListView;
    public TextView buiName;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page);
        Button button_signout = (Button) findViewById(R.id.signOut3);
        final Button btn_navigate = (Button) findViewById(R.id.btn_navigate);
        mListView = (ListView) findViewById(R.id.listview);
        mAuth = FirebaseAuth.getInstance();
        imageView = (ImageView) findViewById(R.id.image);

        showBuisnessPage();
        signOutListener(button_signout);
        navigateLisener(btn_navigate);

    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showBuisnessPage(){
        UtilsBlingBling.getDatabaseReference().child("BusinessUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intent = getIntent();
                String uid = null;
                if(intent != null) {
                    if(intent.hasExtra(CouponAdapter.BUISNESS_ID)) {
                        uid = intent.getStringExtra(CouponAdapter.BUISNESS_ID);
                    }
                }
                if(uid != null){
                    //to add case that the buisness does not exist anymore
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        if(key.equals(uid)) {
                            String name = ds.getValue(BusinessDetails.class).getBusinessName();
                            String adress = ds.getValue(BusinessDetails.class).getBusinessAddress();
                            MyLocation blocation = ds.getValue(BusinessDetails.class).getLocation();
                            UtilsBlingBling.setTargetLocation(blocation);
                            String phone = ds.getValue(BusinessDetails.class).getPhoneNumber();
                            ArrayList<Integer> selected_business_type_items = ds.getValue(BusinessDetails.class).getSelected_business_type_items();
                            ArrayList<String> view = new ArrayList<>();
                            view.add(name);
                            view.add("Address: ".concat(adress));
                            view.add("Phone Number: ".concat(phone));

                            ArrayAdapter adapter = new ArrayAdapter(BusinessPage.this, android.R.layout.simple_list_item_1, view);
                            mListView.setAdapter(adapter);
                            break;
                        }


                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastMessage("on canclled");
            }
        });

    }

    private void signOutListener(Button button_signout){
        button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("signing out....");
                mAuth.signOut();
                startActivity(new Intent(BusinessPage.this, MainActivity.class));
            }
        });
    }
    private void navigateLisener(Button btn_navigate){
        btn_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent( BusinessPage.this,NavigationPop.class));
                MyLocation targetLocation = UtilsBlingBling.getTargetLocation();
                double targetLat = targetLocation.getLatitude();
                double targetLang = targetLocation.getLongitude();
                toastMessage("choose app for directions");

                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=") + android.net.Uri.encode(String.format("%s@%f,%f", label, targetLat, targetLang), "UTF-8");
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));

            }
        });
    }
}
