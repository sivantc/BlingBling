package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CouponsToUse extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CouponDetails> couponDetailsList;
    private int couponTotalNun = 0;
    private int currentCouponNum = 0;
    private String couponCode;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView nav_name;
    private TextView nav_email;
    private ImageView nav_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_to_use);
        recyclerView = (RecyclerView) findViewById(R.id.CouponToUseRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponDetailsList = new ArrayList<>();

        showCouponsToUse();
        navigationDrawer();
    }


    private void showCouponsToUse() {
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child("CouponsUsers").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> couponsRef = (ArrayList<String>) dataSnapshot.getValue();
                        if (couponsRef != null) {
                            for (String couponRef : couponsRef) {
                                couponTotalNun++;
                                String[] parts = couponRef.split("-");
                                couponCode = parts[2];
                                UtilsBlingBling.getDatabaseReference().child("BusinessCoupon").child(parts[0]).child("Coupons").child(parts[1]).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        CouponDetails couponDetails = dataSnapshot.getValue(CouponDetails.class);
                                        if(couponDetails.purchaseCoupon()){
                                            couponDetails.setCouponCode(couponCode);
                                            couponDetailsList.add(couponDetails);
                                        }
                                        else{
                                            toastMessage("coupons out of stock");
                                        }
                                        currentCouponNum++;
                                        if (currentCouponNum == couponTotalNun) {
                                            startPurchasedCouponAdapter();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }


                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void startPurchasedCouponAdapter() {
        if (couponDetailsList.size() > 0) {
            adapter = new CouponToUseAdapter(couponDetailsList, this);
            recyclerView.setAdapter(adapter);
        }
        // else add activity with no coupon to show

    }

    private void navigationDrawer(){
        nav_name = (TextView) findViewById(R.id.name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_bui);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigation_view_bui = (NavigationView) findViewById(R.id.navigation_view_bui);
        View header=navigation_view_bui.getHeaderView(0);
        nav_name = (TextView)header.findViewById(R.id.name);
        nav_email = (TextView)header.findViewById(R.id.email);
        nav_image = (ImageView)header.findViewById(R.id.image);

        UtilsBlingBling.getDatabaseReference().child("BusinessUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                if(uid != null){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        if(key.equals(uid)) {
                            String buiName = ds.getValue(BusinessDetails.class).getBusinessName();
                            nav_name.setText(buiName);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //nav_email.setText("noy's email");
        String uid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        StorageReference storageReference = UtilsBlingBling.getStorageReference().child("images/business/space/" + uid +"/"+ "0" +".jpg");
        //nav_image.setImageResource(getResources().getIdentifier("app_icon", "drawable", getPackageName()));


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
//        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//        roundedBitmapDrawable.setCircular(true);
//        nav_image.setImageDrawable(roundedBitmapDrawable);

        if (storageReference != null) {
            // ImageView in your Activity
            ImageView imageView = nav_image;

            // Load the image using Glide
//            Glide.with(this)
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(imageView);

            Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).transform(new CircleTransform(this)).into(imageView);


        }


        navigation_view_bui.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.my_coupons: {
                        //do somthing
                        break;
                    }
                    case R.id.settings: {
                        //do somthing
                        break;
                    }
                    case R.id.logout: {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CouponsToUse.this, MainActivity.class));
                        break;
                    }
                    case R.id.coupons_to_use: {
                        startActivity(new Intent(CouponsToUse.this, CouponsToUse.class));
                        finish();
                        break;
                    }
                    case R.id.new_coupon: {
                       // createCoupon();
                    }
                }
                //close navigation drawer
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



}

