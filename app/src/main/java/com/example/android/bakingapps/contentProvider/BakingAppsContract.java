package com.example.android.bakingapps.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by robby on 26/07/17.
 */

public class BakingAppsContract
{
    public static final String AUTHORITY = "com.example.android.bakingapps";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String RECIPE_DATA_PATH = "recipeData";
    public static final String INGREDIENT_DATA_PATH = "ingredientData";

    public static final class RecipeDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(RECIPE_DATA_PATH).build();
        public static final String TABLE_RECIPE_DATA = "RecipeData";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVING = "serving";
        public static final String COLUMN_IMAGE_URL = "imageUrl";
    }

    public static final class IngredientDataEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(INGREDIENT_DATA_PATH).build();
        public static final String TABLE_INGREDIENT_DATA = "IngredientData";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_ID_RECIPE = "idRecipe";
    }
}
