package com.blingbling.sivant.blingbling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

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

        long timeCouponIsRelevantInMin = (coupon.getTimeOver() - System.currentTimeMillis())/(1000*60);

        holder.textView_relevantTimeText.setText("coupon will be relevant in the next " + timeCouponIsRelevantInMin + " minutes" );

        holder.button_purchase_coupon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String udid = UtilsBlingBling.getFirebaseAute().getCurrentUser().getUid();
                UtilsBlingBling.getDatabaseReference().child("CouponsUsers").child(udid).child(coupon.getBusinessId() + "-" + couponId + "-" + (int)(Math.random() * 1000000000));
                holder.layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup viewGroup = (ViewGroup) holder.layoutInflater.inflate(R.layout.purchased_coupon_popup, null);
                holder.popupWindow = new PopupWindow(viewGroup, 400, 400, true);
                holder.popupWindow.showAtLocation(holder.couponItemLayout, Gravity.NO_GRAVITY,500,500);
                viewGroup.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        holder.popupWindow.dismiss();
                        return true;
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
        private Button button_more_details;
        private Button button_purchase_coupon;
        private PopupWindow popupWindow;
        private LayoutInflater layoutInflater;
        private LinearLayout couponItemLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            image_view_coupon_image = (ImageView) itemView.findViewById(R.id.image_view_coupon_image);
            ed_price = (TextView) itemView.findViewById(R.id.ed_price);
            ed_description = (TextView) itemView.findViewById(R.id.ed_description);
            textView_relevantTimeText = (TextView) itemView.findViewById(R.id.textView_relevantTimeText);
            button_more_details = (Button) itemView.findViewById(R.id.button_more_details_about_the_business);
            button_purchase_coupon = (Button) itemView.findViewById(R.id.button_purchase_coupon);
            couponItemLayout = (LinearLayout) itemView.findViewById(R.id.couponItemLayout);

        }
    }
}
