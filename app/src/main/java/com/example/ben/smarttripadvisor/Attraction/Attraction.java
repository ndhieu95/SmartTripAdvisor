package com.example.ben.smarttripadvisor.Attraction;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Attraction {
    public String phone_number;
    public String attraction_type;
    public String name;
    public Double price;
    public LatLng position;

    public String opening_time;
    public String closing_time;
    public Double average_rating;
    public String address;
    public String website_url;
    public String description;

    public Attraction(String jsonstring) {
        try {
            JSONObject obj = new JSONObject(jsonstring);


            name = obj.getString("name");
            description = obj.getString("description");
            average_rating = obj.getDouble("average_rating");
            address = obj.getString("address");
            phone_number = obj.getString("phone_number");
            price = obj.getDouble("price");
            website_url = obj.getString("website_url");
            attraction_type = obj.getString("attraction_type");
            opening_time = obj.getString("opening_time");
            closing_time = obj.getString("closing_time");
            position = new LatLng(obj.getDouble("latitude"), obj.getDouble("longitude"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    public String toJSON() throws JSONException {
        JSONObject obj = new JSONObject();


        obj.put("name", name);
        obj.put("description", description);
        obj.put("address", address);
        obj.put("price", price);
        obj.put("phone_number", phone_number);
        obj.put("latitude", position.latitude);
        obj.put("attraction_type", attraction_type);
        obj.put("longitude", position.longitude);
        obj.put("price", price);
        obj.put("website_url", website_url);
        obj.put("opening_time", opening_time);
        obj.put("closing_time", closing_time);
        obj.put("average_rating", average_rating);

        return obj.toString();

    }
}
