package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {
    
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        
        setTitle(sandwich.getMainName());
    }
    
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
    
    private void populateUI(Sandwich sandwich) {
        TextView also_known_tv = findViewById(R.id.also_known_tv);
        TextView description_tv = findViewById(R.id.description_tv);
        TextView origin_tv = findViewById(R.id.origin_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        
        StringBuilder aliases = new StringBuilder();
        //we check for size because when there is only one element it also
        // appends one \n which is not wanted
        for (String s : sandwich.getAlsoKnownAs()) {
            aliases.append(s);
            aliases.append("\n");
        }
        if(sandwich.getAlsoKnownAs().size()>0){
            aliases.setLength(aliases.length() - 1);
        }
        StringBuilder ingredients = new StringBuilder();
        for (String s : sandwich.getIngredients()) {
            ingredients.append(s);
            ingredients.append("\n");
        }
        if(sandwich.getIngredients().size()>0){
            ingredients.setLength(ingredients.length() - 1);
        }
        
        also_known_tv.setText(aliases);
        description_tv.setText(sandwich.getDescription());
        origin_tv.setText(sandwich.getPlaceOfOrigin());
        ingredients_tv.setText(ingredients);
    }
}
