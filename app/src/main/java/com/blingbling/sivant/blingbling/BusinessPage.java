package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusinessPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page);
        Button button_signout = (Button) findViewById(R.id.signOut3);
        mListView = (ListView) findViewById(R.id.listview);


        mAuth = FirebaseAuth.getInstance();
        button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("signing out....");
                mAuth.signOut();
                startActivity(new Intent(BusinessPage.this, MainActivity.class));
            }
        });


            UtilsBlingBling.getDatabaseReference().child("BusniessUsers").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    BusniessDetails bdetails;
                    String uid = "t01Gy9FYsHaGmIXbPqyMzR1uep73";
                    //to add case that the buisness does not exist anymore
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        if(key.equals(uid)) {
                            String name = ds.getValue(BusniessDetails.class).getBusniessName();
                            String adress = ds.getValue(BusniessDetails.class).getBusniessAddress();
                            String phone = ds.getValue(BusniessDetails.class).getPhoneNumber();
                            ArrayList<Integer> selected_busniess_type_items = ds.getValue(BusniessDetails.class).getSelected_busniess_type_items();
                            ArrayList<String> view = new ArrayList<String>();
                            view.add("Busniess Name: ".concat(name));
                            view.add("Busniess Address: ".concat(adress));
                            view.add("Phone Number: ".concat(phone));
                            //setContentView(R.layout.activity_main);

                            ArrayAdapter adapter = new ArrayAdapter(BusinessPage.this,android.R.layout.simple_list_item_1,view);
                            mListView.setAdapter(adapter);
                            break;


                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    toastMessage("on canclled");
                }
            });



    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
