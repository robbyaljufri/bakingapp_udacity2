package com.example.android.bakingapps.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by robby on 29/07/17.
 */

public class BakingAppsContentProvider extends ContentProvider
{
    public static final int RECIPE = 100;
    public static final int RECIPE_ID = 101;

    public static final int INGREDIENT = 200;
    public static final int INGREDIENT_ID = 201;

    private BakingAppsDatabaseHelper bakingAppsDatabaseHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher_ = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher_.addURI(BakingAppsContract.AUTHORITY, BakingAppsContract.RECIPE_DATA_PATH, RECIPE);
        uriMatcher_.addURI(BakingAppsContract.AUTHORITY, BakingAppsContract.RECIPE_DATA_PATH + "/#", RECIPE_ID);

        uriMatcher_.addURI(BakingAppsContract.AUTHORITY, BakingAppsContract.INGREDIENT_DATA_PATH, INGREDIENT);
        uriMatcher_.addURI(BakingAppsContract.AUTHORITY, BakingAppsContract.INGREDIENT_DATA_PATH + "/#", INGREDIENT_ID);

        return uriMatcher_;
    }

    @Override
    public boolean onCreate() {
        bakingAppsDatabaseHelper = new BakingAppsDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase sqliteDatabase = bakingAppsDatabaseHelper.getReadableDatabase();

        int uriMatch = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (uriMatch)
        {
            case RECIPE :
                returnCursor = sqliteDatabase.query
                        (BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            case INGREDIENT :
                returnCursor = sqliteDatabase.query
                        (BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA,
                                projection,
                                selection,
                                selectionArgs,
                                null,
                                null, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase sqliteDatabase = bakingAppsDatabaseHelper.getWritableDatabase();

        int uriMatch = uriMatcher.match(uri);
        Uri returnUri;
        long id;

        switch (uriMatch)
        {
            case RECIPE :
                id = sqliteDatabase.insert(
                        BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            BakingAppsContract.RecipeDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            case INGREDIENT :
                id = sqliteDatabase.insert(
                        BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA,
                        null, values);

                if(id > 0)
                {
                    returnUri = ContentUris.withAppendedId(
                            BakingAppsContract.IngredientDataEntry.CONTENT_URI, id);
                }

                else
                {
                    throw new SQLException("Failed to insert database");
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase sqliteDatabase = bakingAppsDatabaseHelper.getWritableDatabase();
        int uriMatch = uriMatcher.match(uri);

        int dataDeleted = 0;
        String idData;

        switch (uriMatch)
        {
            case RECIPE :
                dataDeleted = sqliteDatabase.delete(BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA,
                        "1", null);
                break;

            case RECIPE_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA,
                        BakingAppsContract.RecipeDataEntry._ID + "=?", new String[]{ idData });
                break;

            case INGREDIENT :
                dataDeleted = sqliteDatabase.delete(BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA,
                        "1", null);
                break;

            case INGREDIENT_ID :
                idData = uri.getPathSegments().get(1);
                dataDeleted = sqliteDatabase.delete(BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA,
                        BakingAppsContract.IngredientDataEntry._ID + "=?", new String[]{ idData });
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        if(dataDeleted != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return dataDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqliteDatabase = bakingAppsDatabaseHelper.getWritableDatabase();
        int uriMatch = uriMatcher.match(uri);

        int dataUpdated = 0;
        String idData;

        switch (uriMatch)
        {
            case RECIPE_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(BakingAppsContract.RecipeDataEntry.TABLE_RECIPE_DATA,
                        values, BakingAppsContract.RecipeDataEntry._ID + "=?", new String[]{ idData });
                break;

            case INGREDIENT_ID :
                idData = uri.getPathSegments().get(1);
                dataUpdated = sqliteDatabase.update(BakingAppsContract.IngredientDataEntry.TABLE_INGREDIENT_DATA,
                        values, BakingAppsContract.IngredientDataEntry._ID+ "=?", new String[]{ idData });
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        if(dataUpdated != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return dataUpdated;
    }
}
