package com.example.android.bakingapps.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



public class Recipe implements Parcelable
{
    String id;
    String name;
    int serving;
    String imageURL;

    public Recipe(String id, String name, int serving, String imageURL) {
        this.id = id;
        this.name = name;
        this.serving = serving;
        this.imageURL = imageURL;
    }

    public Recipe(Parcel in)
    {
        id = in.readString();
        name = in.readString();
        serving = in.readInt();
        imageURL = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServing() {
        return serving;
    }

    public String getImageURL() {
        return imageURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(serving);
        dest.writeString(imageURL);
    }

    public static Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[0];
        }
    };
}
