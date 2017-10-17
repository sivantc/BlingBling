package com.blingbling.sivant.blingbling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CouponToUseAdapter extends RecyclerView.Adapter<CouponToUseAdapter.ViewHolder> {


    private List<CouponDetails> couponList;
    private Context context;


    public CouponToUseAdapter(List<CouponDetails> couponList, Context context) {
        this.couponList = couponList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_to_use_item_list, parent, false);
        return new CouponToUseAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(CouponToUseAdapter.ViewHolder holder, int position) {
        final CouponDetails coupon = couponList.get(position);
        boolean timeOver;
        timeOver = ((coupon.getTimeOver() - System.currentTimeMillis()) < 0) ? true : false;
        holder.ed_description2.setText("description: " + coupon.getDescription());
        holder.ed_price2.setText("price " + coupon.getPrice());
        holder.ed_couponCode2.setText("Coupon code: " + coupon.getCouponCode());
        if (!timeOver) {
            long timeCouponIsRelevantInMin = ((coupon.getTimeOver() - System.currentTimeMillis()) / (1000 * 60) % 60);
            long timeCouponIsRelevantInHours = ((coupon.getTimeOver() - System.currentTimeMillis()) / (1000 * 60 * 60) % 24);

            holder.textView_relevantTimeText2.setText("coupon will be relevant in the next " + timeCouponIsRelevantInHours + " hours and " + timeCouponIsRelevantInMin + " minutes");
        } else {
            holder.textView_relevantTimeText2.setText("Coupon time is over");
        }

        holder.button_use_coupon.setOnClickListener(new View.OnClickListener() {
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

        private TextView ed_price2;
        private TextView ed_description2;
        private TextView textView_relevantTimeText2;
        private ImageView button_use_coupon;
        private TextView ed_couponCode2;

        public ViewHolder(View itemView) {
            super(itemView);
            ed_price2 = (TextView) itemView.findViewById(R.id.ed_price2);
            ed_description2 = (TextView) itemView.findViewById(R.id.ed_description);
            textView_relevantTimeText2 = (TextView) itemView.findViewById(R.id.textView_relevantTimeText2);
            button_use_coupon = (ImageView) itemView.findViewById(R.id.button_use_coupon);
            ed_couponCode2 = (TextView) itemView.findViewById(R.id.ed_couponCode2);

        }
    }
}
