package com.example.user.tapandcook;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import okhttp3.Response;

//generate url to API
public class Service {

    private static final String BASE_URL = "api.edamam.com";
    private static final String API_KEY = "a8c06627d8819deb9e96ecf5823c0ce6";
    private static final String API_ID = "667ff726";


    public static String makeURL(String query, String cookingMethod, String time) {

        String searchTime;

        if(!cookingMethod.equals("Any"))
            query += "+"+cookingMethod;

        if(time.equals("Any"))
            searchTime = "0+";
        else if(time.equals("5 to 15 min"))
            searchTime = "5-15";
        else if(time.equals("15 to 30 min"))
            searchTime = "15-30";
        else if(time.equals("30 to 45 min"))
            searchTime = "30-45";
        else if(time.equals("1 to 2 hours"))
            searchTime = "60-120";
        else
            searchTime = "120+";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(BASE_URL)
                .appendPath("search")
                .appendQueryParameter("q", query)
                .appendQueryParameter("app_id", API_ID)
                .appendQueryParameter("app_key", API_KEY)
                .appendQueryParameter("from", "0")
                .appendQueryParameter("to", "10")
                .appendQueryParameter("time", searchTime);
        String url = builder.build().toString();

        return url;

    }

    public static ArrayList<Recipe> processResults(Response response){
        ArrayList<Recipe> recipeList = new ArrayList<>();
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            if(response.isSuccessful()){
                JSONObject getJSON = new JSONObject(jsonData);
                JSONArray recipesJSON = getJSON.getJSONArray("hits");
                for (int i = 0; i<recipesJSON.length();i++){
                    //gets specific hit
                    JSONObject recipeJSON = recipesJSON.getJSONObject(i);

                    //goes into the recipe portion of hit
                    JSONObject currentRecipe = recipeJSON.getJSONObject("recipe");
                    String uri = currentRecipe.getString("uri");
                    String title = currentRecipe.getString("label");
                    String imageURL = currentRecipe.getString("image");
                    String url = currentRecipe.getString("url");
                    int servings = currentRecipe.getInt("yield");
                    String ingredLines = currentRecipe.getString("ingredientLines");
                    JSONArray ingredientsJSON = currentRecipe.getJSONArray("ingredients");

                    //goes into ingredient portion of recipe
                    for(int j=0; j<ingredientsJSON.length();j++){
                        JSONObject currentIngredient = ingredientsJSON.getJSONObject(j);
                        String text = currentIngredient.getString("text");
                        float weight = BigDecimal.valueOf(currentIngredient.getDouble("weight")).floatValue();

                        Ingredient ingredient = new Ingredient(text, weight);
                        ingredientList.add(ingredient);
                    }

                    Recipe recipe = new Recipe(uri,title,imageURL,url,servings,ingredLines,ingredientList);
                    recipeList.add(recipe);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}
