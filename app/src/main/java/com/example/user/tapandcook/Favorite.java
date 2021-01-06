package com.example.user.tapandcook;

// to store user and their corresponding bookmarked recipe
public class Favorite {
    private int UserID;
    private Recipe recipe;

    public Favorite(int userID, Recipe recipe){
        this.UserID=userID;
        this.recipe=recipe;
    }

    public int getUserID() {
        return UserID;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
