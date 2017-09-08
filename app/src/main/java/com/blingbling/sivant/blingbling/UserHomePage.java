package com.blingbling.sivant.blingbling;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserHomePage extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, View.OnClickListener  {

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private String TAG = "UserHomePage";
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    final private int REQUEST_CODE_ASK_PERMISSIONS = 200;
    private RecyclerView couponRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<CouponDetails> couponDetailsList;
    private Button button_signOut;



    private void relevantCouponQueryDatabase() {
//should contain - relevant distance, relevant preferences about type- will be called when user insert app
        //or when user will change his locatin (in this case we should check if the app is close- send notification
        //or open, and will be called when business will advertise new coupon

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child("Users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserPreferences userPreferences = dataSnapshot.getValue(UserPreferences.class);
                        distanceQuery(userPreferences.getRadius());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

    }
    private void distanceQuery (int userRadius) {
        DatabaseReference referenceToGeoFire = UtilsBlingBling.getDatabaseReference().child("BusniessLocations");
        GeoFire geoFire = new GeoFire(referenceToGeoFire);
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(mLocation.getLatitude(), mLocation.getLongitude()), userRadius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                UtilsBlingBling.countNumOfRelevantBusniess++;
                showBusniessCoupons(key);
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {
                UtilsBlingBling.isLastBusniess = true;
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void showBusniessCoupons(String busniessId) {
        UtilsBlingBling.getDatabaseReference().child("BusniessUsers").child(busniessId).child("Coupons").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot currentBusniessCoupon : dataSnapshot.getChildren()) {
                            CouponDetails couponDetails = currentBusniessCoupon.getValue(CouponDetails.class);
                            couponDetailsList.add(couponDetails);
                        }
                        UtilsBlingBling.countNumOfRetriveBusniessData++;
                        if(UtilsBlingBling.isLastBusniess && UtilsBlingBling.countNumOfRelevantBusniess == UtilsBlingBling.countNumOfRetriveBusniessData) {
                            UtilsBlingBling.isLastBusniess = false;
                            UtilsBlingBling.countNumOfRelevantBusniess = 0;
                            UtilsBlingBling.countNumOfRetriveBusniessData = 0;
                            startAdapter();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );


        //     System.out.print(busniessId);

    }

    private void startAdapter() {
        if (couponDetailsList.size() > 0) {
            adapter = new CouponAdapter(couponDetailsList, this);
            couponRecyclerView.setAdapter(adapter);
        }
        // else add activity with no coupon to show

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        button_signOut = (Button) findViewById(R.id.button_signOut);
        button_signOut.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        couponRecyclerView = (RecyclerView) findViewById(R.id.couponRecyclesView);
        couponRecyclerView.setHasFixedSize(true);
        couponRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponDetailsList = new ArrayList<>();
        handlePermissions();


    }



    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        //      mGoogleApiClient.connect();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        updateLocationInDB(mLocation);

        if (mLocation != null) {
            relevantCouponQueryDatabase();
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
                .setSmallestDisplacement(1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);

        Log.d("reque", "--->>>>");
    }

    private void updateLocationInDB(Location location) {
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        double [] userLocation = {location.getLatitude(), location.getLongitude()};
        UtilsBlingBling.getDatabaseReference().child("Users").child(udid).child("userLocation").setValue(userLocation);
    }
    @Override
    public void onLocationChanged(Location location) {
        updateLocationInDB(location);
    }



    private void handlePermissions() {
        Log.v(TAG, "handlePermissionsAndGetLocation");
        boolean hasWriteContactsPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if (!hasWriteContactsPermission) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserHomePage.this, MainActivity.class));
                break;
        }
    }
}
