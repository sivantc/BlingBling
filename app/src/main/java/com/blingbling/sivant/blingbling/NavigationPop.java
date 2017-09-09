package com.blingbling.sivant.blingbling;

import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.bloder.magic.view.MagicButton;

import static android.R.attr.label;

public class NavigationPop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_pop);
        MagicButton magic_button_maps = (MagicButton) findViewById(R.id.magic_button_maps);
        MagicButton magic_button_waze = (MagicButton) findViewById(R.id.magic_button_waze);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        magic_button_maps.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage("getting directions with Google maps");

                MyLocation targetLocation = UtilsBlingBling.getTargetLocation();
                double currentLattitude = targetLocation.getLatitude();//to get current location and change it
                double currentLongitude = targetLocation.getLongitude();//to get current location and change it
                double targetLat = targetLocation.getLatitude();
                double targetLang = targetLocation.getLongitude();
                String url = "http://maps.google.com/maps?saddr="+currentLattitude+","+currentLongitude+"&daddr="+targetLat+","+targetLang;
               Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
               intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
               startActivity(intent);
            }
        });
        magic_button_waze.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLocation targetLocation = UtilsBlingBling.getTargetLocation();
                double targetLat = targetLocation.getLatitude();
                double targetLang = targetLocation.getLongitude();
                toastMessage("getting directions with Waze");
                //String uri = String.format(Locale.ENGLISH, "geo:0,0?q=") + android.net.Uri.encode(String.format("%s@%f,%f", label, targetLat, targetLang), "UTF-8");

                String uri = "geo: "+String.valueOf(targetLat) + "," + String.valueOf(targetLang);
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));

                /*String uri = "geo: latitude,longtitude"; // this is an option to display all the options to navigate - to ask sivan what she prefer
                startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(uri)));*/
            }
        });

    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
