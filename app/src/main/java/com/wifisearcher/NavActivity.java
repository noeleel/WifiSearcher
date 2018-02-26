package com.wifisearcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NavActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(getApplicationContext(), "Sharing", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(getApplicationContext(), "Favourite", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_notifications:
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
        String WifiKey = myIntent.getStringExtra("WifiIntent");
        TextView Wifiinfo = (TextView)findViewById(R.id.wifi_info);
        Wifiinfo.setText(WifiKey);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
