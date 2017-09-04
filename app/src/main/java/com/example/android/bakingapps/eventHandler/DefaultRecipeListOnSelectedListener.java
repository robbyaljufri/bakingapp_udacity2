package com.example.android.bakingapps.eventHandler;

import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapps.Activity.RecipeDetailActivity;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Recipe;



public class DefaultRecipeListOnSelectedListener implements RecipeListOnSelectedListener
{
    Context context;
    Class targetIntentClass;

    public DefaultRecipeListOnSelectedListener(Context context, Class targetIntentClass) {
        this.context = context;
        this.targetIntentClass = targetIntentClass;
    }

    @Override
    public void OnRecipeSelected(Recipe recipe) {

        Intent intent = new Intent(context, targetIntentClass);
        intent.putExtra(DataKey.RecipeDataKey, recipe);
        context.startActivity(intent);
    }
}
