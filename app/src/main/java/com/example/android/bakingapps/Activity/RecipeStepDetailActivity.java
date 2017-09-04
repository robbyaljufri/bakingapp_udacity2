package com.example.android.bakingapps.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.Fragment.RecipeStepDetailFragment;
import com.example.android.bakingapps.R;

import java.util.ArrayList;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        if(getSupportFragmentManager().findFragmentByTag(DataKey.RecipeStepDetailFragmentKey) == null)
        {
            InitializeFragment(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        InitializeFragment(intent);
    }

    private void InitializeFragment(Intent intent) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DataKey.RecipeStepListKey, GetRecipeDetailListFromIntent(intent));
        bundle.putInt(DataKey.NumberStepKey, GetRecipeNumberFromIntent(intent));

        Fragment recipeStepDetailFragment = new RecipeStepDetailFragment();
        recipeStepDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_recipe_step_detail, recipeStepDetailFragment, DataKey.RecipeStepDetailFragmentKey).commit();
    }


    private ArrayList<RecipeStep> GetRecipeDetailListFromIntent(Intent intent) {
        if (intent.hasExtra(DataKey.RecipeStepListKey)) {
            return intent.getParcelableArrayListExtra(DataKey.RecipeStepListKey);
        } else {
            return null;
        }
    }

    private int GetRecipeNumberFromIntent(Intent intent) {
        if (intent.hasExtra(DataKey.NumberStepKey)) {
            return intent.getIntExtra(DataKey.NumberStepKey, 1);
        } else {
            return 1;
        }
    }

}


    /*MyExoPlayer defaultHttpPlayer;
    ActivityRecipeStepDetailBinding activityRecipeStepDetailBinding;

    ArrayList<RecipeStep> recipeStepArrayList;
    int stepNumber;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRecipeStepDetailBinding = DataBindingUtil.setContentView(this, R.layout.recipe_step_detail_layout);
        defaultHttpPlayer = new MyDefaultHttpExoExoPlayer(activityRecipeStepDetailBinding.expvRecipeStep, this);

        recipeStepArrayList = GetRecipeDetailListFromIntent(getIntent());
        stepNumber = GetRecipeNumberFromIntent(getIntent());

        SetView(recipeStepArrayList.get(stepNumber), stepNumber);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        recipeStepArrayList = GetRecipeDetailListFromIntent(intent);
        stepNumber = GetRecipeNumberFromIntent(intent);

        SetView(recipeStepArrayList.get(stepNumber), stepNumber);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Util.SDK_INT > 23 && recipeStepArrayList != null)
        {
            if(!recipeStepArrayList.get(stepNumber).getVideoURL().isEmpty())
            {
                defaultHttpPlayer.InitializePlayer(recipeStepArrayList.get(stepNumber).getVideoURL());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if((Util.SDK_INT <= 23 || defaultHttpPlayer.getExoPlayer() == null) && recipeStepArrayList.get(stepNumber) != null)
        {
            if(!recipeStepArrayList.get(stepNumber).getVideoURL().isEmpty())
            {
                defaultHttpPlayer.InitializePlayer(recipeStepArrayList.get(stepNumber).getVideoURL());
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(Util.SDK_INT <= 23)
        {
            defaultHttpPlayer.ReleasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(Util.SDK_INT > 23)
        {
            defaultHttpPlayer.ReleasePlayer();
        }
    }

    private void SetView(RecipeStep recipeStep, int stepNumber)
    {
        if(recipeStep != null)
        {
            activityRecipeStepDetailBinding.txtRecipeStepName.setText(recipeStep.getName());
            activityRecipeStepDetailBinding.txtRecipeStepNumber.setText("Step " + String.valueOf(stepNumber + 1));
            activityRecipeStepDetailBinding.txtRecipeStepDescription.setText(recipeStep.getDescription());

            if(stepNumber == 0)
            {
                activityRecipeStepDetailBinding.btnPreviousRecipeStep.setClickable(false);
                activityRecipeStepDetailBinding.btnPreviousRecipeStep.setText("");
            }

            else
            {
                activityRecipeStepDetailBinding.btnPreviousRecipeStep.setClickable(true);
                activityRecipeStepDetailBinding.btnPreviousRecipeStep.setText(getString(R.string.previous_button_label));
                activityRecipeStepDetailBinding.btnPreviousRecipeStep.setOnClickListener(new RecipeStepListOnSelectedListener(recipeStepArrayList, stepNumber - 1, this));
            }

            if(stepNumber == recipeStepArrayList.size() - 1)
            {
                activityRecipeStepDetailBinding.btnNextRecipeStep.setClickable(false);
                activityRecipeStepDetailBinding.btnNextRecipeStep.setText("");
            }

            else
            {
                activityRecipeStepDetailBinding.btnNextRecipeStep.setClickable(true);
                activityRecipeStepDetailBinding.btnNextRecipeStep.setText(getString(R.string.next_button_label));
                activityRecipeStepDetailBinding.btnNextRecipeStep.setOnClickListener(new RecipeStepListOnSelectedListener(recipeStepArrayList, stepNumber + 1, this));
            }
        }
    }

    private ArrayList<RecipeStep> GetRecipeDetailListFromIntent(Intent intent)
    {
        if(intent.hasExtra(DataKey.RecipeStepListKey))
        {
            return intent.getParcelableArrayListExtra(DataKey.RecipeStepListKey);
        }

        else
        {
            return null;
        }
    }

    private int GetRecipeNumberFromIntent(Intent intent)
    {
        if(intent.hasExtra(DataKey.NumberStepKey))
        {
            return intent.getIntExtra(DataKey.NumberStepKey, 1);
        }

        else
        {
            return 1;
        }
    }*/

