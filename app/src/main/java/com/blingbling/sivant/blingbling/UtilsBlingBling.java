package com.blingbling.sivant.blingbling;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UtilsBlingBling extends AppCompatActivity {


    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private static boolean currentlyBusniess = false;
    private static Context currentContextName;
    private static EditText ed_busniess_name;
    private static EditText ed_busniess_address;
    private static EditText ed_phone_number;
    private static Uri uri_filePath_busienssSpace;
    private static ImageView image_view_choosen_image;
    private static Activity currentActivity;
    private static ArrayList<Integer> arrayList_selected_busniess_type_items;
    private static TextView textView_selected_busniess_type;
    private static TextView textViewProgress;
    private static int progressBar;
    private static int currentNum = 0;
    private static Location location;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");




    public static void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        UtilsBlingBling.firebaseAuth = firebaseAuth;
    }

    public static String getProgressBarText() {
        return progressBarText;
    }

    public static void setProgressBarText(String progressBarText) {
        UtilsBlingBling.progressBarText = progressBarText;
    }

    public static String getProgressBarUnit() {
        return progressBarUnit;
    }

    public static void setProgressBarUnit(String progressBarUnit) {
        UtilsBlingBling.progressBarUnit = progressBarUnit;
    }

    private static String progressBarText;
    private static String progressBarUnit;
    public static FirebaseAuth getFirebaseAute() {
        return firebaseAuth;
    }
    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
    public static StorageReference getStorageReference() {
        return storageReference;
    }

    public static boolean isCurrentlyBusniess() {
        return currentlyBusniess;
    }

    public static void setCurrentlyBusniess(boolean currentlyBusniess) {
        UtilsBlingBling.currentlyBusniess = currentlyBusniess;
    }

    public static Context getCurrentContextName() {
        return currentContextName;
    }

    public static void setCurrentContextName(Context currentContextName) {
        UtilsBlingBling.currentContextName = currentContextName;
    }

    public static EditText getEd_busniess_name() {
        return ed_busniess_name;
    }

    public static void setEd_busniess_name(EditText ed_busniess_name) {
        UtilsBlingBling.ed_busniess_name = ed_busniess_name;
    }

    public static EditText getEd_busniess_address() {
        return ed_busniess_address;
    }

    public static void setEd_busniess_address(EditText ed_busniess_address) {
        UtilsBlingBling.ed_busniess_address = ed_busniess_address;
    }

    public static EditText getEd_phone_number() {
        return ed_phone_number;
    }

    public static void setEd_phone_number(EditText ed_phone_number) {
        UtilsBlingBling.ed_phone_number = ed_phone_number;
    }

    public static Uri getUri_filePath_busienssSpace() {
        return uri_filePath_busienssSpace;
    }

    public static void setUri_filePath_busienssSpace(Uri uri_filePath_busienssSpace) {
        UtilsBlingBling.uri_filePath_busienssSpace = uri_filePath_busienssSpace;
    }

    public static ImageView getImage_view_choosen_image() {
        return image_view_choosen_image;
    }

    public static void setImage_view_choosen_image(ImageView image_view_choosen_image) {
        UtilsBlingBling.image_view_choosen_image = image_view_choosen_image;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        UtilsBlingBling.currentActivity = currentActivity;
    }

    public static ArrayList<Integer> getArrayList_selected_busniess_type_items() {
        return arrayList_selected_busniess_type_items;
    }

    public static void setArrayList_selected_busniess_type_items(ArrayList<Integer> arrayList_selected_busniess_type_items) {
        UtilsBlingBling.arrayList_selected_busniess_type_items = arrayList_selected_busniess_type_items;
    }

    public static TextView getTextView_selected_busniess_type() {
        return textView_selected_busniess_type;
    }

    public static void setTextView_selected_busniess_type(TextView textView_selected_busniess_type) {
        UtilsBlingBling.textView_selected_busniess_type = textView_selected_busniess_type;
    }

    public static TextView getTextViewProgress() {
        return textViewProgress;
    }

    public static void setTextViewProgress(TextView textViewProgress) {
        UtilsBlingBling.textViewProgress = textViewProgress;
    }

    public static int getProgressBar() {
        return progressBar;
    }

    public static void setProgressBar(int progressBar) {
        UtilsBlingBling.progressBar = progressBar;
    }
    public static String getCurrentNum() {
        return String.valueOf(++currentNum);
    }

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        UtilsBlingBling.location = location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
