package com.example.ben.smarttripadvisor.Attraction;

import org.json.JSONException;
import org.json.JSONObject;


public class AttractionItem {
    public Integer attraction_id;
    public Double price;
    public String name;
    public String description;

    public AttractionItem(String jstring) throws JSONException {
        JSONObject obj = new JSONObject(jstring);
        attraction_id = obj.getInt("attraction");
        price = obj.getDouble("price");
        name = obj.getString("name");
        description = obj.getString("description");
    }

    public String toJSON() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("attraction_id", attraction_id);
        obj.put("price", price);
        obj.put("name", name);
        obj.put("description", description);
        return obj.toString();
    }

}
