package com.blingbling.sivant.blingbling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.Volley;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity  {
    private EditText ed_password;
    private EditText ed_email;
    private ProgressDialog progress_dialog;
 //   private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_password      = (EditText) findViewById(R.id.ed_password);
        ed_email         = (EditText) findViewById(R.id.ed_email);
        Button bRegister = (Button) findViewById(R.id.button_register);
        progress_dialog = new ProgressDialog(this);
     //   firebaseAuth = FirebaseAuth.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    protected void register(){
        final String password = ed_password.getText().toString().trim();
        final String email    = ed_email.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;

        }
        progress_dialog.setMessage("Registering User....");
        progress_dialog.show();
    //    System.out.print(password);
   //     System.out.print(email);
   //     System.out.print(firebaseAuth.createUserWithEmailAndPassword(email, password).getException());
        UtilsBlingBling.getFirebaseAute().createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registeration Succeed! please click next and choose your preferences", Toast.LENGTH_SHORT).show();
                    Intent regActivityInfo = new Intent(RegisterActivity.this, RegisterActivityInfo.class);
                    startActivity(regActivityInfo);
                }
                else {
                    Log.e("Signup Error", "onCancelled", task.getException());
                    Toast.makeText(RegisterActivity.this, "Registeration fail! please try again", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

