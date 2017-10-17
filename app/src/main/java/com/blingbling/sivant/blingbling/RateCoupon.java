package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class RateCoupon extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button button_submit;
    private TextView textView_clientComment;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView nav_name;
    private TextView nav_email;
    private ImageView nav_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_rating);
        listenerForRatingBar();
        navigationDrawer();

    }

    private void listenerForRatingBar() {
        ratingBar = (RatingBar)  findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                    }
                }
        );
    }

    private void navigationDrawer(){
        nav_name = (TextView) findViewById(R.id.name);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        View header=navigation_view.getHeaderView(0);
        nav_name = (TextView)header.findViewById(R.id.name);
        nav_email = (TextView)header.findViewById(R.id.email);
        nav_image = (ImageView)header.findViewById(R.id.image);
        nav_name.setText(UtilsBlingBling.userName);


        //nav_email.setText("noy's email");
        //nav_image.setImageResource(getResources().getIdentifier("app_icon", "drawable", getPackageName()));
        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        nav_image.setImageDrawable(roundedBitmapDrawable);*/
        String uid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        StorageReference storageReference = UtilsBlingBling.getStorageReference().child("images/business/space/" + uid +"/"+ "0" +".jpg");
        if (storageReference != null) {
            // ImageView in your Activity
            ImageView imageView = nav_image;

            // Load the image using Glide
//            Glide.with(this)
//                    .using(new FirebaseImageLoader())
//                    .load(storageReference)
//                    .into(imageView);

            Glide.with(this).using(new FirebaseImageLoader()).load(storageReference).transform(new CircleTransform(this)).error(R.drawable.profile2).into(imageView);


        }

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.my_coupons: {
                        startActivity(new Intent(RateCoupon.this, PurchasedCoupons.class));
                        finish();
                        break;
                    }
                    case R.id.av_coupons: {
                        startActivity(new Intent(RateCoupon.this, UserHomePage.class));
                        finish();
                        break;
                    }
                    case R.id.settings: {
                        //do somthing
                        break;
                    }
                    case R.id.logout: {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(RateCoupon.this, MainActivity.class));
                        finish();
                        break;
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
}
