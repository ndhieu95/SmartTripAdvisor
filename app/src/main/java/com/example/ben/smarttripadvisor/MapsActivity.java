package com.example.ben.smarttripadvisor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ben.smarttripadvisor.Attraction.Attraction;
import com.example.ben.smarttripadvisor.Attraction.AttractionStorage;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                try {
                    setUpMap();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setUpMap() throws JSONException, IOException {
        mMap.setMyLocationEnabled(true);
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline= "";
        String result = "";
        try {
            while ((eachline = bufferedReader.readLine()) !=null)
            {
                result = result + eachline;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            AttractionStorage.getInstance().LoadAttraction(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            if (AttractionStorage.getInstance().AttractionList[i] != null) {
                LatLng pos = AttractionStorage.getInstance().AttractionList[i].position;
                mMap.addMarker(new MarkerOptions().position(pos).title(AttractionStorage.getInstance().AttractionList[i].name));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please choose attraction's location", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng position) {
                    mMap.addMarker(new MarkerOptions().position(position));

                    Intent intent = new Intent(MapsActivity.this, AddActivity.class);
                    intent.putExtra("latitude", position.latitude);
                    intent.putExtra("longitude", position.longitude);

                    startActivity(intent);
                    mMap.setOnMapClickListener(null);
                }
            });

            return true;
        }

        if (id == R.id.action_list) {
            Intent intent = new Intent(MapsActivity.this, ListActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //confirm khi chon location
    public void Confirm(){
        new AlertDialog.Builder(MapsActivity.this)
                .setTitle("Confirm Location")
                .setMessage("Are you sure about this location?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
