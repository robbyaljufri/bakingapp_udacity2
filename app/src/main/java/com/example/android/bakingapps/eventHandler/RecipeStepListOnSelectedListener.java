package com.example.android.bakingapps.eventHandler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.bakingapps.Activity.RecipeStepDetailActivity;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;

import java.util.ArrayList;



public interface RecipeStepListOnSelectedListener
{
    void OnRecipeStepSelected(ArrayList<RecipeStep> recipeSteps, int stepNumber);
}
