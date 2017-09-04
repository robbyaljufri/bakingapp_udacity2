package com.example.android.bakingapps.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.Fragment.RecipeDetailFragment;
import com.example.android.bakingapps.Fragment.RecipeStepDetailFragment;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.eventHandler.RecipeStepListOnSelectedListener;

import java.util.ArrayList;

public class RecipeDetailForTabletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_for_tablet);

        InitializeRecipeDetailFragment(GetDataFromIntent(getIntent()));
    }

    private void InitializeRecipeDetailFragment(Recipe recipe)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DataKey.RecipeDataKey, recipe);

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        recipeDetailFragment.setRecipeStepListOnSelectedListener(new RecipeStepListTabletOnSelectedListener());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_recipe_detail, recipeDetailFragment, DataKey.RecipeDetailFragmentKey).commit();
    }

    private void InitializeRecipeStepDetailFragment(Integer stepNumber, ArrayList<RecipeStep> recipeStepArrayList) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DataKey.RecipeStepListKey, recipeStepArrayList);
        bundle.putInt(DataKey.NumberStepKey, stepNumber);

        RecipeStepDetailFragment recipeStepDetailFragment = new RecipeStepDetailFragment();
        recipeStepDetailFragment.setArguments(bundle);
        recipeStepDetailFragment.setRecipeStepListOnSelectedListener(new RecipeStepListTabletOnSelectedListener());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_recipe_step_detail, recipeStepDetailFragment, DataKey.RecipeStepDetailFragmentKey).commit();
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

    public class RecipeStepListTabletOnSelectedListener implements RecipeStepListOnSelectedListener
    {
        @Override
        public void OnRecipeStepSelected(ArrayList<RecipeStep> recipeSteps, int stepNumber) {
            InitializeRecipeStepDetailFragment(stepNumber, recipeSteps);
        }
    }
}
