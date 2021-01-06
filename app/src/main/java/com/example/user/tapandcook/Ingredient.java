package com.example.user.tapandcook;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable{
    String text;
    float weight;

    public Ingredient(String text, float weight){
        this.text = text;
        this.weight = weight;
    }

    public String getText(){return text;}

    public float getWeight(){return weight;}

    protected Ingredient(Parcel in) {
        text = in.readString();
        weight = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    //flatten object to parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(text);
    dest.writeFloat(weight);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
    //parcel : a message container for lightweight, high-performance Inter-process communication (IPC)