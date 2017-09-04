package com.example.android.bakingapps.eventHandler;

import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapps.Activity.RecipeStepDetailActivity;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;

import java.util.ArrayList;



public class DefaultPhoneRecipeStepListOnSelectedListener implements RecipeStepListOnSelectedListener
{
    Context context;

    public DefaultPhoneRecipeStepListOnSelectedListener(Context context) {
        this.context = context;
    }

    @Override
    public void OnRecipeStepSelected(ArrayList<RecipeStep> recipeSteps, int stepNumber) {
        Intent intent = new Intent(context, RecipeStepDetailActivity.class);

        intent.putExtra(DataKey.RecipeStepListKey, recipeSteps);
        intent.putExtra(DataKey.NumberStepKey, stepNumber);

        context.startActivity(intent);
    }
}
