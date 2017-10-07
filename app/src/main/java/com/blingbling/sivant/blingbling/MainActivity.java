package com.blingbling.sivant.blingbling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends AppCompatActivity{
    private EditText ed_password;
    private EditText ed_email;
    private ProgressDialog progress_dialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 2;
    GoogleApiClient mGoogleApiClient;
    CallbackManager mCallbackManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_email = (EditText) findViewById(R.id.ed_email);
        TextView register = (TextView) findViewById(R.id.register);
        TextView forgot_password = (TextView) findViewById(R.id.forgot_password);
        Button button_login = (Button) findViewById(R.id.button_login);
        SignInButton google_signIn_btn = (SignInButton) findViewById(R.id.google_signIn_btn);
        progress_dialog = new ProgressDialog(this);
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        registerListener(register);
        logInListener(button_login);
        showMatchFirstPage(button_login);
        googleSignInListener(google_signIn_btn);
        facebookSignInListener();
        forgotPasswordListener(forgot_password,mAuth);

    }

    private void forgotPasswordListener(TextView forgot_password, FirebaseAuth mauth) {
        //String emailAddress = "user@example.com";
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final String email = ed_email.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    //Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    toastMessage("Please enter your email");
                    return;
                }
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    toastMessage("Email sent to you in order to reset youre password");
                                    Log.d(TAG, "Email sent.");
                                }
                            }
                        });

            }
        });

    }

    private void facebookSignInListener(){
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_btn);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            toastMessage("facebook successfuly loged in");
                            /*// Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                        } else {
                            toastMessage("facebook login failed");
                           /* // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);*/
                        }

                        // ...
                    }
                });
    }

    private void googleSignInListener(SignInButton google_signIn_btn){
        google_signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        toastMessage("somthing went wrong");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                toastMessage("Auth went wrong");
            }
        }
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void registerListener(TextView register){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register(){
        UtilsBlingBling.setNotRegistering(false);
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
                    Intent buiOrBui = new Intent(MainActivity.this, BuisOrUserReg.class);
                    startActivity(buiOrBui);
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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null&& UtilsBlingBling.getNotRegisternig()) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

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
        UtilsBlingBling.getDatabaseReference().child("BusinessUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                //toastMessage("uid is: " +uid);
               // toastMessage("dataSnapshot.hasChild(uid): "+ dataSnapshot.hasChild(uid));
                if (dataSnapshot.hasChild(uid)) {
                   toastMessage("business");
                    startActivity(new Intent(MainActivity.this, BusinessMenu.class));
                }
                else{
               //     mAuth.signOut();
                    toastMessage("login user");
                    UtilsBlingBling.getDatabaseReference().child("Users").child(uid).child("deviceToken").setValue(FirebaseInstanceId.getInstance().getToken());
                    UserPreferences userPreferences = dataSnapshot.getValue(UserPreferences.class);
                    UtilsBlingBling.getDatabaseReference().child("Users").child(uid).addListenerForSingleValueEvent(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserPreferences userPreferences = dataSnapshot.getValue(UserPreferences.class);
                                    if(userPreferences.getRadius()==-1||userPreferences.getFirstname()==null||userPreferences.getLastname()==null){
                                        toastMessage("you have to insert some details");
                                        startActivity(new Intent(MainActivity.this, RegisterActivityInfo.class));
                                    }
                                    else {
                                        startActivity(new Intent(MainActivity.this, UserHomePage.class));
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            }
                    );

                }

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


