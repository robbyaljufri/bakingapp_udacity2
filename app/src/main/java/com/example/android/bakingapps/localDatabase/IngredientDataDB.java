package com.example.android.bakingapps.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.contentProvider.BakingAppsContract;

import java.util.ArrayList;



public class IngredientDataDB extends DataDB<Ingredient>
{
    /*public IngredientDataDB(SQLiteDatabase sqLiteDatabase)
    {
        super(sqLiteDatabase);
    }*/

    public IngredientDataDB(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Ingredient> getAllData() {

        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                BakingAppsContract.IngredientDataEntry.CONTENT_URI,
                null,
                null,
                null,
                BakingAppsContract.IngredientDataEntry._ID);

        if(!cursor.moveToFirst())
        {
            return null;
        }

        do
        {
            String name = cursor.getString(cursor.getColumnIndex(BakingAppsContract.IngredientDataEntry.COLUMN_NAME));
            String measure = cursor.getString(cursor.getColumnIndex(BakingAppsContract.IngredientDataEntry.COLUMN_MEASURE));
            double quantity = cursor.getDouble(cursor.getColumnIndex(BakingAppsContract.IngredientDataEntry.COLUMN_QUANTITY));
            String idRecipe = cursor.getString(cursor.getColumnIndex(BakingAppsContract.IngredientDataEntry.COLUMN_ID_RECIPE));

            ingredientArrayList.add(new Ingredient(name, measure, quantity, idRecipe));

        } while (cursor.moveToNext());

        cursor.close();

        return ingredientArrayList;
    }

    @Override
    public void addData(Ingredient ingredient) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BakingAppsContract.IngredientDataEntry.COLUMN_NAME, ingredient.getName());
        contentValues.put(BakingAppsContract.IngredientDataEntry.COLUMN_MEASURE, ingredient.getMeasure());
        contentValues.put(BakingAppsContract.IngredientDataEntry.COLUMN_QUANTITY, ingredient.getQuantity());
        contentValues.put(BakingAppsContract.IngredientDataEntry.COLUMN_ID_RECIPE, ingredient.getIdRecipe());

        context.getContentResolver().insert(BakingAppsContract.IngredientDataEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void removeData(String idData) {
        Uri uri = BakingAppsContract.IngredientDataEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idData).build();

        context.getContentResolver().delete(uri, null, null);
    }

    @Override
    public void removeAllData() {
        Uri uri = BakingAppsContract.IngredientDataEntry.CONTENT_URI;
        context.getContentResolver().delete(uri, null, null);
    }
}
