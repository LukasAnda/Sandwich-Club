package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.MainActivity;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static String NAME = "name";
    private static String ALIAS = "alsoKnownAs";
    private static String INGREDIENTS = "ingredients";
    private static String MAIN_NAME = "mainName";
    private static String PLACE = "placeOfOrigin";
    private static String DESCRIPTION = "description";
    private static String IMAGE = "image";
    
    
    public static Sandwich parseSandwichJson(String json) {
        try {
            //Get JSON objects for each part of the JSON supplied
            JSONObject sandwichJson = new JSONObject(json);
            JSONObject nameJson = sandwichJson.getJSONObject(NAME);
            JSONArray aliasesJson = nameJson.getJSONArray(ALIAS);
            JSONArray ingredientsJson = sandwichJson.getJSONArray(INGREDIENTS);
            
            //We already have almost all the info to construct the Sandwich object,
            //just need to create String lists
            String name = nameJson.getString(MAIN_NAME);
            String place = sandwichJson.getString(PLACE);
            String description = sandwichJson.getString(DESCRIPTION);
            String image = sandwichJson.getString(IMAGE);
            List<String> aliases = new ArrayList<>();
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < aliasesJson.length(); i++) {
                aliases.add(aliasesJson.getString(i));
            }
            for (int i = 0; i < ingredientsJson.length(); i++) {
                ingredients.add(ingredientsJson.getString(i));
            }
            
            //return new object based on our data
            return new Sandwich(name,aliases,place,description,image,ingredients);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
