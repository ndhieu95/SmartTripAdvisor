package com.example.ben.smarttripadvisor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ben.smarttripadvisor.Attraction.Attraction;
import com.example.ben.smarttripadvisor.Attraction.AttractionStorage;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    EditText Name;
    EditText Description;
    EditText Address;
    EditText Phone;
    EditText Opening_Time;
    EditText Closing_Time;
    EditText website;
    RatingBar ratingBar;
    String price = ("1");
    double rating = 5;
    LatLng position;
    Double lat, lon,pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        HandleRatingBar();
        HandleSpinner();
        Name = (EditText) findViewById(R.id.Name);
        Description = (EditText) findViewById(R.id.Description);
        Address = (EditText) findViewById(R.id.Address);
        Phone = (EditText) findViewById(R.id.Phone);
        Opening_Time = (EditText) findViewById(R.id.Opening_Time);
        Closing_Time = (EditText) findViewById(R.id.Closing_Time);
        position = new LatLng(getIntent().getDoubleExtra("latitude", 0), getIntent().getDoubleExtra("longitude", 0));
        website = (EditText) findViewById(R.id.WebsiteURL);
    }

    public void HandleRatingBar(){
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);

                Toast.makeText(getApplicationContext(), "Rating: " + String.valueOf(rating), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void HandleSpinner(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.attractiontype, android.R.layout.simple_dropdown_item_1line);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_done){
/*            OutputStream outputStream;
            AttractionStorage.getInstance().AddAttraction(new Attraction(
                    Phone.getText().toString(),
                    spinner.getSelectedItem().toString(),
                    Name.getText().toString(),
                    Opening_Time.getText().toString(),
                    Closing_Time.getText().toString(),
                    ratingBar.getRating(),
                    Address.getText().toString(),
                    website.getText().toString(),
                    Description.getText().toString()*/


            //writeData(AttractionStorage.getInstance().toString());

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView myText = (TextView) view;
        myText.getText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void writeData(String a){
        BufferedWriter bufferedWriter= null;
        try {
            FileOutputStream fileOutputStream = openFileOutput("Data", Context.MODE_APPEND);
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(a);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
