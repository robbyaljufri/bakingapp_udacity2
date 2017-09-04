package com.example.android.bakingapps.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.Fragment.RecipeDetailFragment;
import com.example.android.bakingapps.Fragment.RecipeStepDetailFragment;
import com.example.android.bakingapps.R;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(getSupportFragmentManager().findFragmentByTag(DataKey.RecipeDetailFragmentKey) == null)
        {
            InitializeFragment(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        InitializeFragment(intent);
    }

    private void InitializeFragment(Intent intent)
    {
        Recipe recipe = GetDataFromIntent(intent);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DataKey.RecipeDataKey, recipe);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_recipe_detail, recipeDetailFragment, DataKey.RecipeDetailFragmentKey).commit();
    }

    private Recipe GetDataFromIntent(Intent intent)
    {
        if(intent != null)
        {
            if(intent.hasExtra(DataKey.RecipeDataKey))
            {
                return intent.getParcelableExtra(DataKey.RecipeDataKey);
            }
        }

        return null;
    }
}
