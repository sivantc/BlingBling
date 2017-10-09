package com.blingbling.sivant.blingbling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PurchasedCoupons extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<CouponDetails> couponDetailsList;
    private int couponTotalNun = 0;
    private int currentCouponNum = 0;
    private String couponCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_coupons);

        recyclerView = (RecyclerView) findViewById(R.id.purchasedCouponRecyclesView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        couponDetailsList = new ArrayList<>();

        showPurchasedCoupons();
    }



    private void showPurchasedCoupons() {
        String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
        UtilsBlingBling.getDatabaseReference().child("CouponsUsers").child(udid).addListenerForSingleValueEvent(
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
                                        couponDetails.setCouponCode(couponCode);
                                        couponDetailsList.add(couponDetails);
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
            adapter = new PurchasedCouponsAdapter(couponDetailsList, this);
            recyclerView.setAdapter(adapter);
        }
        // else add activity with no coupon to show

    }




}
