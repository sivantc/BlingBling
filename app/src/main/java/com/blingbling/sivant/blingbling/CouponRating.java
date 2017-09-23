package com.blingbling.sivant.blingbling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class CouponRating extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button button_submit;
    private TextView textView_clientComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_rating);
        listenerForRatingBar();
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
}
