package com.blingbling.sivant.blingbling;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sivan on 18/08/2017.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private List<CouponDetails> couponList;
    private Context context;
    private String couponId;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Reference to an image file in Cloud Storage
        CouponDetails coupon = couponList.get(position);
        String couponId = coupon.getCouponId();
        StorageReference storageReference = UtilsBlingBling.getStorageReference().child("images/busniess/space/" + coupon.getBusniessId() +"/"+ couponId +".jpg");

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

        long timeCouponIsRelevantInMin = (coupon.getTimeOver() - System.currentTimeMillis())/(1000*60);

        holder.textView_relevantTimeText.setText("coupon will be relevant in the next " + timeCouponIsRelevantInMin + " minutes" );

        
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_view_coupon_image;
        public TextView ed_price;
        public TextView ed_description;
        public TextView textView_relevantTimeText;
        public Button button_more_details;

        public ViewHolder(View itemView) {
            super(itemView);

            image_view_coupon_image = (ImageView) itemView.findViewById(R.id.image_view_coupon_image);
            ed_price = (TextView) itemView.findViewById(R.id.ed_price);
            ed_description = (TextView) itemView.findViewById(R.id.ed_description);
            textView_relevantTimeText = (TextView) itemView.findViewById(R.id.textView_relevantTimeText);
            button_more_details = (Button) itemView.findViewById(R.id.button_more_details);

        }
    }
}
