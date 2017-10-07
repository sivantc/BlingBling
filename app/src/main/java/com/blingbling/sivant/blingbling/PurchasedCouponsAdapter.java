package com.blingbling.sivant.blingbling;

import android.content.Context;
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
    public PurchasedCouponsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_coupons_item_list, parent, false);
        return new PurchasedCouponsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CouponDetails coupon = couponList.get(position);
        final String couponId = coupon.getCouponId();
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ed_price;
        private TextView ed_description;
        private TextView textView_relevantTimeText;
        private Button button_more_details;
        private TextView ed_couponCode;

        public ViewHolder(View itemView) {
            super(itemView);
            ed_price = (TextView) itemView.findViewById(R.id.ed_price);
            ed_description = (TextView) itemView.findViewById(R.id.ed_description);
            textView_relevantTimeText = (TextView) itemView.findViewById(R.id.textView_relevantTimeText);
            button_more_details = (Button) itemView.findViewById(R.id.button_rate_coupon);
            ed_couponCode = (TextView) itemView.findViewById(R.id.ed_couponCode);

        }
    }
}
