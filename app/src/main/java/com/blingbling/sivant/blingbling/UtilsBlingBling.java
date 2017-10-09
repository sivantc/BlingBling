package com.blingbling.sivant.blingbling;

import android.app.Activity;
import android.content.Context;
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
    private static boolean currentlyBusiness = false;
    private static Context currentContextName;
    private static EditText ed_business_name;
    private static EditText ed_business_address;
    private static EditText ed_phone_number;
    private static Uri uri_filePath_busienssSpace;
    private static ImageView image_view_choosen_image;
    private static Activity currentActivity;
    private static ArrayList<Integer> arrayList_selected_business_type_items;
    private static TextView textView_selected_business_type;
    private static TextView textViewProgress;
    private static int progressBar;
    private static int currentNum = 0;
    private static MyLocation location;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
    private static boolean notRegistering = true;
    public static boolean isLastBusiness = false;
    public static int countNumOfRelevantBusiness = 0;
    public static int countNumOfRetriveBusinessData = 0;
    private static MyLocation targetLocation;
    private static String buisness_coupon_id;
    public static int currCouponId = 0;

    public static int getNextCouponId(){
        int prev = currCouponId;
        prev++;
        setCurrCouponId(prev);
        return prev;
    }
    public static void setCurrCouponId(int curr){
        currCouponId = curr;
    }
    public static int getCurrCouponId(){
        return currCouponId;
    }


    public static void setBuisness_coupon_id(String bid){
        buisness_coupon_id = bid;
    }
    public  static String getBuisness_coupon_id(){
        return  buisness_coupon_id;
    }


    public static void setTargetLocation(MyLocation location){
        targetLocation = location;
    }
    public  static MyLocation getTargetLocation(){
        return  targetLocation;
    }

    public static void setNotRegistering(boolean b){
        notRegistering = b;
    }
    public  static boolean getNotRegisternig(){
        return  notRegistering;
    }


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

    public static boolean isCurrentlyBusiness() {
        return currentlyBusiness;
    }

    public static void setCurrentlyBusiness(boolean currentlyBusiness) {
        UtilsBlingBling.currentlyBusiness = currentlyBusiness;
    }

    public static Context getCurrentContextName() {
        return currentContextName;
    }

    public static void setCurrentContextName(Context currentContextName) {
        UtilsBlingBling.currentContextName = currentContextName;
    }

    public static EditText getEd_business_name() {
        return ed_business_name;
    }

    public static void setEd_business_name(EditText ed_business_name) {
        UtilsBlingBling.ed_business_name = ed_business_name;
    }

    public static EditText getEd_business_address() {
        return ed_business_address;
    }

    public static void setEd_business_address(EditText ed_business_address) {
        UtilsBlingBling.ed_business_address = ed_business_address;
    }

    public static EditText getEd_phone_number() {
        return ed_phone_number;
    }

    public static void setEd_phone_number(EditText ed_phone_number) {
        UtilsBlingBling.ed_phone_number = ed_phone_number;
    }

    public static Uri getUri_filePath_businessSpace() {
        return uri_filePath_busienssSpace;
    }

    public static void setUri_filePath_businessSpace(Uri uri_filePath_busienssSpace) {
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

    public static ArrayList<Integer> getArrayList_selected_business_type_items() {
        return arrayList_selected_business_type_items;
    }

    public static void setArrayList_selected_business_type_items(ArrayList<Integer> arrayList_selected_business_type_items) {
        UtilsBlingBling.arrayList_selected_business_type_items = arrayList_selected_business_type_items;
    }

    public static TextView getTextView_selected_business_type() {
        return textView_selected_business_type;
    }

    public static void setTextView_selected_business_type(TextView textView_selected_business_type) {
        UtilsBlingBling.textView_selected_business_type = textView_selected_business_type;
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

    public static MyLocation getLocation() {
        return location;
    }

    public static void setLocation(MyLocation location) {
        UtilsBlingBling.location = location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




}
