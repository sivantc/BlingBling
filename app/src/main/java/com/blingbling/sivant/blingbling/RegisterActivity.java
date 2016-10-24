
package com.blingbling.sivant.blingbling;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText ed_name = (EditText) findViewById(R.id.ed_name);
        final EditText ed_lastname = (EditText) findViewById(R.id.ed_lastname);
        final EditText ed_username = (EditText) findViewById(R.id.ed_username);
        final EditText ed_password = (EditText) findViewById(R.id.ed_password);
        final EditText ed_email = (EditText) findViewById(R.id.ed_email);
//        final EditText etRadios = (EditText) findViewById(R.id.etRadios);
//        final EditText etPop = (EditText) findViewById(R.id.etPop);

        final Button bRegister = (Button) findViewById(R.id.button_register);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = ed_name.getText().toString();
                final String lastname = ed_lastname.getText().toString();
                final String username = ed_username.getText().toString();
                final String password = ed_password.getText().toString();
                final String email = ed_email.getText().toString();
//                final int radios = Integer.parseInt(etRadios.getText().toString());
//                final EditText popup = (EditText) findViewById(R.id.etPop);

                // Check if any of the field are not empty.
                if (firstname.equals("") || lastname.equals("") || username.equals("")|| password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Check if both passwords matches
//                if (!password.equals(password)) {
//                    Toast.makeText(getApplicationContext(), "Password does not match",
//                            Toast.LENGTH_LONG).show();
//                    return;
//                }

                // Check if the user name is exists
                SQLiteDatabase mydatabase = openOrCreateDatabase("server29.000webhost.com",MODE_PRIVATE,null);
                String query = "select * from Client where user_name = " + username + ";";
                Cursor resultSet = mydatabase.rawQuery(query,null);
                resultSet.moveToFirst();
                if(resultSet.isAfterLast()) {
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            // if the registration succeed than the registration will take to the main
                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(firstname, lastname,username,password,email,0,false, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
