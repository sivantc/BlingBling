
package com.blingbling.sivant.blingbling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private static SeekBar seekBar_distance;
    private static TextView km;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
