package com.example.catwiki.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.catwiki.Adapter.FavAdapter;
import com.example.catwiki.Database;
import com.example.catwiki.Model.Cat;
import com.example.catwiki.Model.Image;
import com.example.catwiki.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatDetailActivity extends AppCompatActivity {

    private ImageView catPhoto;
    private TextView catName;
    private TextView catDescription;
    private TextView catWeight;
    private TextView catTemperament;
    private TextView catOrigin;
    private TextView catLifespan;
    private TextView catDogFriend;
    private TextView link;
    private ArrayList<Image> imageArrayList;
    private String imageUrl;
    private Button favButton;
    private String toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_detail);
        catName = findViewById(R.id.cat_name);
        catDescription = findViewById(R.id.cat_description);
        catOrigin = findViewById(R.id.cat_origin);
        catWeight = findViewById(R.id.cat_weight);
        catTemperament = findViewById(R.id.cat_temperament);
        catLifespan = findViewById(R.id.cat_lifespan);
        catDogFriend = findViewById(R.id.cat_dogfriendliness);
        catPhoto = findViewById(R.id.cat_photo);
        favButton = findViewById(R.id.cat_button);
        link = findViewById(R.id.link);

        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        final Cat cat = Database.getCatById(id);
        toast = cat.getName();
        catName.setText(cat.getName());
        catDescription.setText(cat.getDescription());
        catOrigin.setText(cat.getOrigin());
        catWeight.setText(cat.getWeight().getMetric() + " kg");
        catTemperament.setText(cat.getTemperament());
        catLifespan.setText(cat.getLife_span() + " years");
        catDogFriend.setText(String.valueOf(cat.getDog_friendly()));


        String potentialUrl = "https://api.thecatapi.com/v1/images/search?breed_ids=" + cat.getId();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Image[] imageArray = gson.fromJson(response, Image[].class);
                imageArrayList = new ArrayList<>(Arrays.asList(imageArray));
                Image thisImage = imageArrayList.get(0);
                imageUrl = thisImage.getUrl();
                Glide.with(CatDetailActivity.this).load(imageUrl).into(catPhoto);

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                System.out.println(error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, potentialUrl, responseListener, errorListener);
        requestQueue.add(stringRequest);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cat.getWikipedia_url()));
                startActivity(browserIntent);
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FavAdapter.favCats.contains(cat)){
                    Toast.makeText(CatDetailActivity.this, toast + " already in favourites!", Toast.LENGTH_SHORT).show();
                } else {
                    FavAdapter.favCats.add(cat);
                    Toast.makeText(CatDetailActivity.this, toast + " has been added to favourites!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
