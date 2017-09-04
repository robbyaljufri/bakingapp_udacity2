package com.example.android.bakingapps.eventHandler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.bakingapps.Activity.RecipeDetailActivity;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;



public interface RecipeListOnSelectedListener
{
    void OnRecipeSelected(Recipe recipe);
}
