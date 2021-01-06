package com.example.user.tapandcook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class bookmarkdetail extends AppCompatActivity {

    private Recipe recipe;
    private TextView title;
    private ImageView image;
    private TextView info;
    private TextView ingredient;
    private Button detailbutton;
    private ImageButton notbookmarkedbtn;
    private ArrayList<Favorite> favoritelist;
    private SharedPreferences preferences;
    private SharedPreferences namepref;
    private DBHandler dbHandler=new DBHandler(this);
    private Favorite favorite;
    private int UserID;
    private String username;
    private boolean bookmark=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailrecipe);

        namepref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        recipe = getIntent().getParcelableExtra("recipe");
        username=namepref.getString("name",null);
        UserID=dbHandler.checkuserID(username);
        favorite=new Favorite(UserID,recipe);

        //notbookmarkedbtn is set to VISIBLE by default
        //if recipe is bookmarked, set bookmarkedbtn to VISIBLE and not bookmarkedbtn to INVISIBLE

        title = findViewById(R.id.title);
        image = findViewById(R.id.image);
        info = findViewById(R.id.info);
        ingredient = findViewById(R.id.ingredient);
        detailbutton = findViewById(R.id.button);
        notbookmarkedbtn = findViewById(R.id.notbookmarked);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Picasso.get().load(recipe.getImageURL()).into(image);
        title.setText(recipe.getTitle());
        info.setText("Serving size: "+ recipe.getServing() + "pax");
        ingredient.setText(recipe.getIngTextWeight());

        //get url to access for web view
        detailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = recipe.getUrl();
                if(url.startsWith("http"))
                    url = url.replace("http","https");
                Intent intent = new Intent(bookmarkdetail.this, webRecipe.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });


        //if selected item is in bookmarked
        if(preferences.contains("Favorite"))
            favoritelist=Favoritelist();

        notbookmarkedbtn.setImageResource(R.drawable.bookmarked);
        notbookmarkedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //remove bookmark
                notbookmarkedbtn.setImageResource(R.drawable.notbookmarked);
                Toast.makeText(bookmarkdetail.this, "Removed from bookmark", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < favoritelist.size(); i++) {
                    if (favoritelist.get(i).getRecipe().getTitle().equals(recipe.getTitle())) {
                        favoritelist.remove(i);
                        bookmark = false;
                        Gson gson = new Gson();
                        String json = gson.toJson(favoritelist);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Favorite", json);
                        editor.commit();
                        break;
                    }

                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //get bookmark list
    public ArrayList<Favorite> Favoritelist(){
        ArrayList <Favorite> bookmarklist;

        String json=preferences.getString("Favorite",null);
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Favorite>>(){}.getType();
        bookmarklist=gson.fromJson(json,type);

        return bookmarklist;
    }
}
