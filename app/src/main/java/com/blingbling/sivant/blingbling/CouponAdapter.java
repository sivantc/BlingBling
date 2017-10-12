package com.blingbling.sivant.blingbling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sivan on 18/08/2017.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private List<CouponDetails> couponList;
    private Context context;
    public final static String BUISNESS_ID = "BUISNESS_ID";



    public CouponAdapter(List<CouponDetails> couponList, Context context) {
        this.couponList = couponList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Reference to an image file in Cloud Storage
        final CouponDetails coupon = couponList.get(position);
        final String couponId = coupon.getCouponId();
        StorageReference storageReference = UtilsBlingBling.getStorageReference().child("images/business/space/" + coupon.getBusinessId() +"/"+ couponId +".jpg");

    if (storageReference != null) {
    // ImageView in your Activity
        ImageView imageView = holder.image_view_coupon_image;


    // Load the image using Glide
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }
        holder.ed_description.setText(coupon.getDescription());
        holder.ed_price.setText(coupon.getPrice());

        holder.button_more_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), BusinessPage.class);
                intent.putExtra(BUISNESS_ID, coupon.getBusinessId());
                view.getContext().startActivity(intent);


            }
        });
        holder.ed_couponId.setText(couponId);

        long timeCouponIsRelevantInMin = ((coupon.getTimeOver() - System.currentTimeMillis())/(1000*60) % 60);
        long timeCouponIsRelevantInHours =((coupon.getTimeOver() - System.currentTimeMillis())/(1000*60*60) % 24);



        holder.textView_relevantTimeText.setText(timeCouponIsRelevantInHours+ " hours and "  + timeCouponIsRelevantInMin + " minutes left" );

        final Animation animScale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_scale);

        holder.button_purchase_coupon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.startAnimation(animScale);
                String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                UtilsBlingBling.getDatabaseReference().child("CouponsUsers").child(udid).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayList <String> couponsRef = (ArrayList<String>)dataSnapshot.getValue();
                                int couponCode = (int)(Math.random() * 1000000000);
                                if (couponsRef == null) {
                                    couponsRef = new ArrayList<String>();
                                }
                                couponsRef.add(coupon.getBusinessId() + "-" + couponId + "-" + couponCode);
                                UtilsBlingBling.getDatabaseReference().child("CouponsUsers").child(UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid()).setValue(couponsRef);
                                holder.layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                ViewGroup viewGroup = (ViewGroup) holder.layoutInflater.inflate(R.layout.purchased_coupon_popup, null);
                                holder.popupWindow = new PopupWindow(viewGroup, 1500, 1500, true);
                                holder.popupWindow.showAtLocation(holder.couponItemLayout, Gravity.CENTER,0,0);
                                viewGroup.setOnTouchListener(new View.OnTouchListener() {

                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                        holder.popupWindow.dismiss();
                                        return true;
                                    }
                                });                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


            }
        });
    }


    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_view_coupon_image;
        private TextView ed_price;
        private TextView ed_description;
        private TextView textView_relevantTimeText;
        private TextView button_more_details;
        private ImageView button_purchase_coupon;
        private PopupWindow popupWindow;
        private LayoutInflater layoutInflater;
        private LinearLayout couponItemLayout;
        private TextView ed_couponId;

        public ViewHolder(View itemView) {
            super(itemView);

            image_view_coupon_image = (ImageView) itemView.findViewById(R.id.image_view_coupon_image);
            ed_price = (TextView) itemView.findViewById(R.id.ed_price);
            ed_couponId = (TextView) itemView.findViewById(R.id.ed_couponId);
            ed_description = (TextView) itemView.findViewById(R.id.ed_description);
            textView_relevantTimeText = (TextView) itemView.findViewById(R.id.textView_relevantTimeText);
            button_more_details = (TextView) itemView.findViewById(R.id.button_more_details_about_the_business);
            button_purchase_coupon = (ImageView) itemView.findViewById(R.id.button_purchase_coupon);
            couponItemLayout = (LinearLayout) itemView.findViewById(R.id.couponItemLayout);

        }
    }
}
