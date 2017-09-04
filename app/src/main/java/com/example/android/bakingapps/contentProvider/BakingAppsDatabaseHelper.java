package com.example.android.bakingapps.contentProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by robby on 26/07/17.
 */

public class BakingAppsDatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "BakingApps.db";
    public static final int DATABASE_VERSION = 1;

    public BakingAppsDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQLITE_CREATE_RECIPE_TABLE = "CREATE TABLE " + BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA + " (" +
                BakingAppsContract.RecipeDataEntry._ID + " INTEGER PRIMARY KEY, " +
                BakingAppsContract.RecipeDataEntry.COLUMN_NAME + " TEXT, " +
                BakingAppsContract.RecipeDataEntry.COLUMN_SERVING + " INTEGER, " +
                BakingAppsContract.RecipeDataEntry.COLUMN_IMAGE_URL +  " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_RECIPE_TABLE);

        final String SQLITE_CREATE_INGREDIENT_TABLE = "CREATE TABLE " + BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA + " (" +
                BakingAppsContract.IngredientDataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BakingAppsContract.IngredientDataEntry.COLUMN_NAME + " TEXT, " +
                BakingAppsContract.IngredientDataEntry.COLUMN_MEASURE + " TEXT, " +
                BakingAppsContract.IngredientDataEntry.COLUMN_QUANTITY + " DOUBLE, " +
                BakingAppsContract.IngredientDataEntry.COLUMN_ID_RECIPE +  " TEXT " +
                ");";

        db.execSQL(SQLITE_CREATE_INGREDIENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA);

        onCreate(db);
    }
}
