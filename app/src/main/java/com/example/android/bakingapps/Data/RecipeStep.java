package com.example.android.bakingapps.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;



public class RecipeStep implements Parcelable
{
    String name;
    String description;
    String videoURL;
    String thumbnailURL;
    String idRecipe;

    public RecipeStep(String name, String description, String videoURL, String thumbnailURL, String idRecipe) {
        this.name = name;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.idRecipe = idRecipe;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getIdRecipe() {
        return idRecipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
        dest.writeString(idRecipe);
    }

    public static Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
    @Override
    public RecipeStep createFromParcel(Parcel source) {
        return new RecipeStep(source);
    }

    @Override
    public RecipeStep[] newArray(int size) {
        return new RecipeStep[0];
    }

    };

    public RecipeStep(Parcel in) {
        name = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
        idRecipe = in.readString();
    }

}
