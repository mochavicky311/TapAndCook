package com.example.user.tapandcook;

//store recipe info
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Recipe implements Parcelable{
    String uri;
    String title;
    String imageURL;
    String url;
    int serving;
    String ingredLines;
    ArrayList<Ingredient> ingredients;

    public Recipe(String uri, String title, String imageURL, String url, int serving, String ingredLines, ArrayList<Ingredient> ingredients) {
        this.uri = uri;
        this.title = title;
        this.imageURL = imageURL;
        this.url = url;
        this.serving = serving;
        this.ingredLines = ingredLines;
        this.ingredients = ingredients;
    }

    protected Recipe(Parcel in){
        uri = in.readString();
        title = in.readString();
        imageURL = in.readString();
        url = in.readString();
        serving = in.readInt();
        ingredLines = in.readString();
        ingredients = in.readArrayList(Ingredient.class.getClassLoader());
    }

    public String getTitle(){
        return title;
    }

    public String getImageURL(){
        return imageURL;
    }

    public String getUrl(){return url;}

    public int getServing(){return serving;}

    public String getIngTextWeight(){
        String IngTextWeight = "";
        String weight;
        String text;

        for(int i=0; i<ingredients.size(); i++){
            text = ingredients.get(i).getText();
            weight = String.valueOf(ingredients.get(i).getWeight());
            IngTextWeight += (text + " " + weight + "\n\n");
        }

        return IngTextWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //flatten object to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
        dest.writeString(title);
        dest.writeString(imageURL);
        dest.writeString(url);
        dest.writeInt(serving);
        dest.writeString(ingredLines);
        dest.writeList(ingredients);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}

//parcel : a message container for lightweight, high-performance Inter-process communication (IPC)
