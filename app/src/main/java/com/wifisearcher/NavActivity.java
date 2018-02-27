package com.wifisearcher;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.content.Intent.EXTRA_TEXT;

public class NavActivity extends AppCompatActivity {

    private String WifiKey;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_share:
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    share.putExtra(Intent.EXTRA_SUBJECT, "None");
                    share.putExtra(EXTRA_TEXT, WifiKey.toString());

                    startActivity(Intent.createChooser(share, "Super access point!"));
                    Toast.makeText(getApplicationContext(), "Partage en cours", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_favorite:
                    Intent fav = new Intent(getApplicationContext(), FavouriteActivity.class);
                    fav.putExtra("WifiNew", WifiKey.toString());
                    startActivity(fav);

                    Toast.makeText(getApplicationContext(), "Favourite", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_directions:
                    Intent myIntent = getIntent();
                    String Markerlocation = myIntent.getStringExtra("MarkerLocation");
                    String Currentlocation = myIntent.getStringExtra("CurrentLocation");
                    double sourceLatitude = Double.parseDouble(Currentlocation.split("  ")[0]);
                    double sourceLongitude = Double.parseDouble(Currentlocation.split("  ")[1]);
                    double destinationLatitude = Double.parseDouble(Markerlocation.split("  ")[0]);
                    double destinationLongitude = Double.parseDouble(Markerlocation.split("  ")[1]);
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", sourceLatitude, sourceLongitude, "Current Location", destinationLatitude, destinationLongitude, "Marker location");
                    Intent directions = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    directions.setPackage("com.google.android.apps.maps");
                    startActivity(directions);
                    Toast.makeText(getApplicationContext(), "Directions", Toast.LENGTH_LONG).show();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Intent myIntent = getIntent();
        WifiKey = myIntent.getStringExtra("WifiIntent");
        TextView Wifiinfo = (TextView)findViewById(R.id.wifi_info);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        float currentbatterielevel =  (batteryPct*100);
        float pastbatterielevel = Float.parseFloat(myIntent.getStringExtra("PastBatterieLevel"));

        String Batterie_info = "\nNiveau de la batterie au commencement de l'application : "
                + Float.toString(pastbatterielevel) + "%"
                + "\n \n Niveau de la batterie apres lancement de l'application : "
                + Float.toString(currentbatterielevel) + "%"
                + "\n \n \n L'application a utilisé " + Float.toString(currentbatterielevel - pastbatterielevel) + " % de votre batterie.";
        Wifiinfo.setText(WifiKey + Batterie_info);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
