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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.Locale;

import static android.R.attr.label;

public class BusinessPage extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mListView;
    public TextView buiName;
    private ImageView imageView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView nav_name;
    private TextView nav_email;
    private ImageView nav_image;
    private Button call_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page);
        final Button btn_navigate = (Button) findViewById(R.id.btn_navigate);
        mListView = (ListView) findViewById(R.id.listview);
        mAuth = FirebaseAuth.getInstance();
        imageView = (ImageView) findViewById(R.id.image);
        call_btn = (Button) findViewById(R.id.call_btn);

        showBuisnessPage();
        navigateLisener(btn_navigate);
        navigationDrawer();



    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void showBuisnessPage(){
        UtilsBlingBling.getDatabaseReference().child("BusinessUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intent = getIntent();
                String uid = null;
                if(intent != null) {
                    if(intent.hasExtra(CouponAdapter.BUISNESS_ID)) {
                        uid = intent.getStringExtra(CouponAdapter.BUISNESS_ID);
                    }
                }
                if(uid != null){
                    //to add case that the buisness does not exist anymore
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        if(key.equals(uid)) {
                            String name = ds.getValue(BusinessDetails.class).getBusinessName();
                            String adress = ds.getValue(BusinessDetails.class).getBusinessAddress();
                            MyLocation blocation = ds.getValue(BusinessDetails.class).getLocation();
                            UtilsBlingBling.setTargetLocation(blocation);
                            String phone = ds.getValue(BusinessDetails.class).getPhoneNumber();
                            ArrayList<Integer> selected_business_type_items = ds.getValue(BusinessDetails.class).getSelected_business_type_items();
                            ArrayList<String> view = new ArrayList<>();
                            view.add(name);
                            view.add("Address: ".concat(adress));
                            view.add("Phone Number: ".concat(phone));
                            StorageReference storageReference = UtilsBlingBling.getStorageReference().child("images/business/space/" + uid +"/"+ "0" +".jpg");
                            //ImageView imageView2 = imageView;


                            Glide.with(BusinessPage.this).using(new FirebaseImageLoader()).load(storageReference).into(imageView);


                            ArrayAdapter adapter = new ArrayAdapter(BusinessPage.this, android.R.layout.simple_list_item_1, view);
                            mListView.setAdapter(adapter);
                            //callListener(phone);
                            break;
                        }


                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastMessage("on canclled");
            }
        });

    }

    private void navigateLisener(Button btn_navigate){
        btn_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent( BusinessPage.this,NavigationPop.class));
                MyLocation targetLocation = UtilsBlingBling.getTargetLocation();
                double targetLat = targetLocation.getLatitude();
                double targetLang = targetLocation.getLongitude();
                toastMessage("choose app for directions");

                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=") + android.net.Uri.encode(String.format("%s@%f,%f", label, targetLat, targetLang), "UTF-8");
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));

            }
        });
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

        UtilsBlingBling.getDatabaseReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                if(uid != null){
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        if(key.equals(uid)) {
                            String firstname = ds.getValue(UserPreferences.class).getFirstname();
                            String lastname = ds.getValue(UserPreferences.class).getLastname();
                            String username = firstname.concat(" ").concat(lastname);
                            nav_name.setText(username);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        nav_email.setText("noy's email");
        //nav_image.setImageResource(getResources().getIdentifier("app_icon", "drawable", getPackageName()));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        nav_image.setImageDrawable(roundedBitmapDrawable);

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                        startActivity(new Intent(BusinessPage.this, MainActivity.class));
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
   /* private void callListener(final String phone){
        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "tel:".concat(phone);
                Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse(tel));
                startActivity(callIntent);

            }
        });
    }*/
}
