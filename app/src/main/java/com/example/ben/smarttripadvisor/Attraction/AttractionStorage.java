package com.example.ben.smarttripadvisor.Attraction;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.List;

public class AttractionStorage {

    private static AttractionStorage instance;
    public static AttractionStorage getInstance() {
        if (instance == null)
            instance = new AttractionStorage();
        return instance;
    }

    private AttractionStorage() {

    }

    int lastAttractionId, lastItemId;
    public Attraction[] AttractionList = new Attraction[1000];
    public AttractionItem[] AttractionItemList = new AttractionItem[1000];



    public void LoadAttraction(String jstring) throws JSONException {
        JSONArray arr = new JSONArray(jstring);
        JSONObject obj;

        lastAttractionId = 0;
        lastItemId = 0;

        for(int i = 0; i < arr.length(); i++){
            obj = arr.getJSONObject(i);
            int pk = obj.getInt("pk");
            if (obj.getString("model").compareTo("staservice.basedattraction") == 0) {
                AttractionList[pk] = new Attraction(obj.getJSONObject("fields").toString());
                if (lastAttractionId < pk)
                    lastAttractionId = pk;
            } else {
                AttractionItemList[pk] = new AttractionItem(obj.getJSONObject("fields").toString());
                if (lastItemId < pk)
                    lastItemId = pk;
            }
        }
    }

    public void AddAttraction(Attraction attraction) {
        AttractionList[++lastAttractionId] = attraction;
    }

    public void AddAttractionItem(AttractionItem item) {
        AttractionItemList[++lastItemId] = item;
    }






}
