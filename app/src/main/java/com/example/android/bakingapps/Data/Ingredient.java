package com.example.android.bakingapps.Data;

import android.os.Parcel;
import android.os.Parcelable;



public class Ingredient implements Parcelable
{
    String name;
    String measure;
    double quantity;
    String idRecipe;

    public Ingredient(String name, String measure, double quantity, String idRecipe) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
        this.idRecipe = idRecipe;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getIdRecipe() {
        return idRecipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Ingredient> CREATOR = new Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }

    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(measure);
        dest.writeDouble(quantity);
    }

    public Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        quantity = in.readInt();
    }
}
