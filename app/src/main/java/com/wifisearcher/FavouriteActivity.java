package com.wifisearcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FavouriteActivity extends AppCompatActivity {
    String NewFavourite;
    String favList;
    TextView FavouriteList;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent myIntent = getIntent();
        NewFavourite = myIntent.getStringExtra("WifiNew");
        favList = getFavouriteList();
        add_to_favourite(NewFavourite);
        FavouriteList = (TextView)findViewById(R.id.wifi_info);
        FavouriteList.setText(favList);

    }

    private String getFavouriteList ( ) {
        String favouriteList = "";
        String filename = "favouritelist";
        FileInputStream inputStream;
        File file;

        try {
            file = new File(filename);
            file.createNewFile();
            inputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            inputStream.read(data);
            favouriteList = new String(data);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favouriteList;
    }

    private void add_to_favourite (String newFavourite) {
        String filename = "favouritelist";
        String add_favourite = NewFavourite;
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(add_favourite.getBytes());
            outputStream.close();
            favList = FavouriteList + "\n" + NewFavourite;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
