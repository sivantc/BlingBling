package com.blingbling.sivant.blingbling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.google.android.gms.drive.query.Filters.contains;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText ed_password;
    private EditText ed_email;
    private ProgressDialog progress_dialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "MainActivity";

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_password      = (EditText) findViewById(R.id.ed_password);
        ed_email         = (EditText) findViewById(R.id.ed_email);
        Button  button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(this);
        Button  button_business = (Button) findViewById(R.id.button_business_register);
        Button button_login = (Button) findViewById(R.id.button_login);
        Button button_logout = (Button) findViewById(R.id.button_logout);
        button_business.setOnClickListener(this);
        progress_dialog = new ProgressDialog(this);

        logInListener(button_login);
        logOutListener(button_logout);
        showMatchFirstPage(button_login);

    }

        public void onClick (View v) {
            switch(v.getId()) {
                case R.id.button_register:
                    UtilsBlingBling.setCurrentlyBusniess(false);
                    UtilsBlingBling.setNotRegistering(false);
                    register();
                    break;
                case R.id.button_business_register:
                    UtilsBlingBling.setCurrentlyBusniess(true);
                    UtilsBlingBling.setNotRegistering(false);
                    register();
                    break;
            }
        }

    private void register() {
        final String password = ed_password.getText().toString().trim();
        final String email = ed_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;

        }
        progress_dialog.setMessage("Registering User....");
        progress_dialog.show();
        UtilsBlingBling.getFirebaseAute().createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progress_dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Registeration Succeed!", Toast.LENGTH_SHORT).show();
                    Intent regActivityInfo;
                    if (!UtilsBlingBling.isCurrentlyBusniess()) {
                        toastMessage("I am here");
                        regActivityInfo = new Intent(MainActivity.this, RegisterActivityInfo.class);
                    } else
                        regActivityInfo = new Intent(MainActivity.this, BusniessRegisterActivityInfo.class);
                        startActivity(regActivityInfo);
                } else {
                    progress_dialog.dismiss();
                    Log.e("Signup Error", "onCancelled", task.getException());
                    System.out.print(task.getException());
                    Toast.makeText(MainActivity.this, "Registeration fail!" + task.getException() + "please try again", Toast.LENGTH_SHORT).show();

                }
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Here you get the error type
                        Log.d(TAG + "-On Failure", e.getMessage());
                    }

                    ;
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showMatchFirstPage(Button button_login){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null&& UtilsBlingBling.getNotRegisternig()) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("buisness"+ UtilsBlingBling.isBusniessUser());

                    // toastMessage("successfully signed in with " + user.getEmail());
                   // check = true;
                    checkIfBuisness();

                } else {
                    //check =false;
                    //checkIfBuisness();
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    //toastMessage("successfully signed out ");
                }
                // ...
            }
        };
       // logInListener(button_login);

    }

    private void checkIfBuisness(){
        UtilsBlingBling.getDatabaseReference().child("BusniessUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  if(!check) return;
                String uid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                //toastMessage("uid is: " +uid);
               // toastMessage("dataSnapshot.hasChild(uid): "+ dataSnapshot.hasChild(uid));
                if (dataSnapshot.hasChild(uid)) {
                   toastMessage("buisness");
                    startActivity(new Intent(MainActivity.this, BusniessMenu.class));
                }
                else{
                    toastMessage("login user");
                    String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                    UtilsBlingBling.getDatabaseReference().child("Users").child(udid).child("deviceToken").setValue(FirebaseInstanceId.getInstance().getToken());
                    startActivity(new Intent(MainActivity.this, UserHomePage.class));
                }
               // check = false;
                //UtilsBlingBling.setIsBusniessUser(true);
                // toastMessage(" UtilsBlingBling.isBusniessUser(): "+ UtilsBlingBling.isBusniessUser());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastMessage("on canclled");
            }
        });
    }

    private void logInListener(Button button_login){
        button_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = ed_email.getText().toString();
                String pass = ed_password.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                    toastMessage("fields are empty");
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                toastMessage("sign in problem");
                            }
                            else{
                                toastMessage("signing in....");
                                String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                                UtilsBlingBling.getDatabaseReference().child("Users").child(udid).child("deviceToken").setValue(FirebaseInstanceId.getInstance().getToken());
                                startActivity(new Intent(MainActivity.this, UserHomePage.class));
                            }
                        }
                    });
                }
            }
        });
    }

    private void logOutListener(Button button_logout){
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("signing out....");
                mAuth.signOut();
            }
        });
    }

}


