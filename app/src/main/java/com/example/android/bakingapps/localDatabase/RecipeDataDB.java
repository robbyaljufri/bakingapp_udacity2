package com.example.android.bakingapps.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.contentProvider.BakingAppsContract;

import java.util.ArrayList;



public class RecipeDataDB extends DataDB<Recipe>
{
    String id;
    String name;
    int serving;
    String imageURL;

    public RecipeDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Recipe> getAllData()
    {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                BakingAppsContract.RecipeDataEntry.CONTENT_URI,
                null,
                null,
                null,
                BakingAppsContract.RecipeDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String id = cursor.getString(cursor.getColumnIndex(BakingAppsContract.RecipeDataEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(BakingAppsContract.RecipeDataEntry.COLUMN_NAME));
            int serving = cursor.getInt(cursor.getColumnIndex(BakingAppsContract.RecipeDataEntry.COLUMN_SERVING));
            String imageURL = cursor.getString(cursor.getColumnIndex(BakingAppsContract.RecipeDataEntry.COLUMN_IMAGE_URL));

            recipeArrayList.add(new Recipe(id, name, serving, imageURL));

        } while (cursor.moveToNext());

        cursor.close();

        return recipeArrayList;
    }

    @Override
    public void addData(Recipe recipe) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(BakingAppsContract.RecipeDataEntry._ID, recipe.getId());
        contentValues.put(BakingAppsContract.RecipeDataEntry.COLUMN_NAME, recipe.getName());
        contentValues.put(BakingAppsContract.RecipeDataEntry.COLUMN_SERVING, recipe.getServing());
        contentValues.put(BakingAppsContract.RecipeDataEntry.COLUMN_IMAGE_URL, recipe.getImageURL());

        context.getContentResolver().insert(BakingAppsContract.RecipeDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = BakingAppsContract.RecipeDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = BakingAppsContract.RecipeDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
