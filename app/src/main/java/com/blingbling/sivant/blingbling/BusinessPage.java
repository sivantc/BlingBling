package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusinessPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page);
        Button button_signout = (Button) findViewById(R.id.signOut3);

        mAuth = FirebaseAuth.getInstance();
        button_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("signing out....");
                mAuth.signOut();
                startActivity(new Intent(BusinessPage.this, MainActivity.class));
            }
        });


            UtilsBlingBling.getDatabaseReference().child("BusniessUser").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    BusniessDetails bdetails;
                    toastMessage("hi1");
                    String uid = "KuqzjXr7thgVWpnbj1EMqsCAWea2";
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        bdetails = ds.child(uid).getValue(BusniessDetails.class);
                       // String ok = "ok";
                       // toastMessage("bdetails: "+ bdetails.toString());
                        //String name = bdetails.getBusniessName();

                           // String name = ds.child(uid).getValue(BusniessDetails.class).getBusniessName();
                            //toastMessage("name is: "+ name);


                        //String adress = dataSnapshot.child(uid).getValue(BusniessDetails.class).getBusniessAddress();
                        //String phone = dataSnapshot.child(uid).getValue(BusniessDetails.class).getPhoneNumber();
                        //ArrayList<Integer> selected_busniess_type_items = dataSnapshot.child(uid).getValue(BusniessDetails.class).getSelected_busniess_type_items();


                        // bdetails = new BusniessDetails(name,adress,phone,selected_busniess_type_items);
                        //toastMessage("name: "+ds.child(uid).getValue(BusniessDetails.class).getBusniessName() );
                    }


                    toastMessage("hi");
                    boolean b = dataSnapshot.hasChild(uid);
                    toastMessage("this is b: " + b);
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
