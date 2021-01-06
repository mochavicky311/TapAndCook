package com.example.user.tapandcook;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class bookmarklist extends AppCompatActivity implements bookmarkadapter.ItemClickListener {
    private bookmarkadapter adapter;
    private RecyclerView recipeList;
    private ArrayList<Favorite> favoriteList;
    private ArrayList<Recipe> userfavorite=new ArrayList<>();
    private DBHandler dbHandler=new DBHandler(this);
    SharedPreferences preferences,namepref;
    String username;
    private View frame;
    int UserID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarklist);

            namepref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            username=namepref.getString("name",null);
            UserID=dbHandler.checkuserID(username);
            preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        frame=findViewById(R.id.frame3);
            //display all result of bookmarked list
        if(preferences.contains("Favorite")) {
            String json = preferences.getString("Favorite", null);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Favorite>>() {
            }.getType();
            favoriteList = gson.fromJson(json, type);
            for (int i = 0; i < favoriteList.size(); i++) {
                if (favoriteList.get(i).getUserID() == UserID) {
                    userfavorite.add(favoriteList.get(i).getRecipe());
                }

            }
        }

            //set adapter for the bookmark list to be displayed at the entire layout
            recipeList = findViewById(R.id.results2);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recipeList.setLayoutManager(layoutManager);
            recipeList.setHasFixedSize(true);
            adapter = new bookmarkadapter(userfavorite,  this);
            recipeList.setAdapter(adapter);

    }

    @Override
    public void onListenItemClick(int clickedItemIndex) {

        //link the recipe's detail information after clicked on entire recipe
        Recipe clickedRecipe = userfavorite.get(clickedItemIndex);

        Intent intent = new Intent(bookmarklist.this,bookmarkdetail.class);
        intent.putExtra("recipe",clickedRecipe);
        startActivity(intent);
        Snackbar refresh=Snackbar.make(frame,"Any changes will be saved after leaving this page.",
                Snackbar.LENGTH_LONG );
        refresh.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(bookmarklist.this,homepage.class);
        startActivity(intent);
        finish();

    }
};

