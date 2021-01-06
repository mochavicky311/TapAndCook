package com.example.user.tapandcook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;


public class listActivity extends AppCompatActivity implements listAdapter.ItemClickListener{

    private listAdapter adapter;
    private RecyclerView resultList;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list);

            //display list of searching result
            recipeList = getIntent().getParcelableArrayListExtra("recipe");
            resultList = findViewById(R.id.results);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            resultList.setLayoutManager(layoutManager);
            resultList.setHasFixedSize(true);
            adapter = new listAdapter(recipeList,this);
            resultList.setAdapter(adapter);
        }

    @Override
    public void onListenItemClick(int clickedItemIndex) {

        Recipe clickedRecipe = recipeList.get(clickedItemIndex);

        //link to detail of recipe
        Intent intent = new Intent(listActivity.this, detailRecipeActivity.class);
        intent.putExtra("recipe",clickedRecipe);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}