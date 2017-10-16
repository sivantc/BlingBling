package com.blingbling.sivant.blingbling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PurchasedCouponsAdapter extends RecyclerView.Adapter<PurchasedCouponsAdapter.ViewHolder>{
    private List<CouponDetails> couponList;
    private Context context;


    public PurchasedCouponsAdapter(List<CouponDetails> couponList, Context context) {
        this.couponList = couponList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_coupons_item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CouponDetails coupon = couponList.get(position);
        boolean timeOver;
        timeOver = ((coupon.getTimeOver() - System.currentTimeMillis()) < 0) ? true : false;
        holder.ed_description.setText("description: " + coupon.getDescription());
        holder.ed_price.setText("price " + coupon.getPrice());
        holder.ed_couponCode.setText("Coupon code: " + coupon.getCouponCode());
        if (!timeOver) {
            long timeCouponIsRelevantInMin = ((coupon.getTimeOver() - System.currentTimeMillis()) / (1000 * 60) % 60);
            long timeCouponIsRelevantInHours = ((coupon.getTimeOver() - System.currentTimeMillis()) / (1000 * 60 * 60) % 24);

            holder.textView_relevantTimeText.setText("coupon will be relevant in the next " + timeCouponIsRelevantInHours + " hours and " + timeCouponIsRelevantInMin + " minutes");
        } else {
            holder.textView_relevantTimeText.setText("Coupon time is over");
        }

        holder.button_rate_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), RateCoupon.class);
                view.getContext().startActivity(intent);


            }
        });


    }



    @Override
    public int getItemCount() {
        return couponList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ed_price;
        private TextView ed_description;
        private TextView textView_relevantTimeText;
        private ImageView button_rate_coupon;
        private TextView ed_couponCode;

        public ViewHolder(View itemView) {
            super(itemView);
            ed_price = (TextView) itemView.findViewById(R.id.ed_price);
            ed_description = (TextView) itemView.findViewById(R.id.ed_description);
            textView_relevantTimeText = (TextView) itemView.findViewById(R.id.textView_relevantTimeText);
            button_rate_coupon = (ImageView) itemView.findViewById(R.id.button_rate_coupon);
            ed_couponCode = (TextView) itemView.findViewById(R.id.ed_couponCode);

        }
    }
}
