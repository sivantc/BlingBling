package com.blingbling.sivant.blingbling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UtilsBlingBling extends AppCompatActivity {
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    public static FirebaseAuth getFirebaseAute() {
        return firebaseAuth;
    }
    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
    public static StorageReference getStorageReference() {
        return storageReference;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
