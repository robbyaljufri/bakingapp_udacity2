package com.example.android.bakingapps.Fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.android.bakingapps.Adapter.IngredientListAdapter;
import com.example.android.bakingapps.Adapter.RecipeStepListAdapter;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.asyncTask.AsyncTaskCompleteListener;
import com.example.android.bakingapps.asyncTask.NetworkDataGetterSyncTask;
import com.example.android.bakingapps.eventHandler.DefaultPhoneRecipeStepListOnSelectedListener;
import com.example.android.bakingapps.eventHandler.RecipeStepListOnSelectedListener;
import com.example.android.bakingapps.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.bakingapps.jsonParsing.IngredientListJSONParser;
import com.example.android.bakingapps.jsonParsing.RecipeStepListJSONParser;

import java.util.ArrayList;



public class RecipeDetailFragment extends Fragment
{
    boolean expandState = false;

    boolean dataHasBeenLoad;
    String recipeListURL = "http://go.udacity.com/android-baking-app-json";
    boolean internetAccessState = false;

    Recipe recipe;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<RecipeStep> recipeStepArrayList;
    RecipeStepListOnSelectedListener recipeStepListOnSelectedListener;

    RecyclerView ingredientRecyclerView;
    RecyclerView recipeStepRecyclerView;
    ScrollView recipeDetailRootLayout;
    ConstraintLayout noInternetAccessLayout;
    ConstraintLayout loadingDataLayout;
    CardView ingredientCardView;
    Button tryAgainButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_detail_layout, container, false);

        ingredientRecyclerView = (RecyclerView) view.findViewById(R.id.rv_ingredient_list);
        recipeStepRecyclerView = (RecyclerView) view.findViewById(R.id.rv_recipe_step_list);
        recipeDetailRootLayout = (ScrollView) view.findViewById(R.id.root_recipe_detail_layout);
        noInternetAccessLayout = (ConstraintLayout) view.findViewById(R.id.no_internet_access_recipe_detail_layout);
        loadingDataLayout = (ConstraintLayout) view.findViewById(R.id.loading_data_recipe_detail_layout);

        ingredientCardView = (CardView) view.findViewById(R.id.cv_recipe_ingredients);
        ingredientCardView.setOnClickListener(new CardViewOnClickListener());

        tryAgainButton = (Button) view.findViewById(R.id.btn_try_again);
        tryAgainButton.setOnClickListener(new TryAgainClickListener());

        SetInitialData(savedInstanceState);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(DataKey.InternetAccessStateKey, internetAccessState);

        if(recipe != null)
        {
            outState.putParcelable(DataKey.RecipeDataListKey, recipe);
        }

        if(ingredientArrayList != null)
        {
            if(!ingredientArrayList.isEmpty())
            {
                outState.putParcelableArrayList(DataKey.IngredientListKey, ingredientArrayList);
            }
        }

        if(recipeStepArrayList != null)
        {
            if(!recipeStepArrayList.isEmpty())
            {
                outState.putParcelableArrayList(DataKey.RecipeStepListKey, recipeStepArrayList);
            }
        }
    }

    /*public void setOnClickListener(RecipeDetailForTabletActivity.RecipeStepListTabletClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }*/

    public void setRecipeStepListOnSelectedListener(RecipeStepListOnSelectedListener recipeStepListOnSelectedListener) {
        this.recipeStepListOnSelectedListener = recipeStepListOnSelectedListener;
    }

    private void SetInitialData(Bundle savedInstanceState)
    {
        recipe = GetDataFromIntent(getArguments());

        if(savedInstanceState != null)
        {
            if(CheckNetworkStateDataPersistance(savedInstanceState))
            {
                internetAccessState = GetNetworkStateDataPersistance(savedInstanceState);

                if(internetAccessState)
                {
                    recipe = GetRecipeDataPersistance(savedInstanceState);
                    ingredientArrayList = GetIngredientListDataPersistance(savedInstanceState);
                    recipeStepArrayList = GetRecipeStepListDataPersistance(savedInstanceState);

                    SetIngredientRecyclerView(ingredientArrayList);
                    SetRecipeStepRecyclerView(recipeStepArrayList);

                    return;
                }
            }
        }

        else
        {
            if(NetworkConnectionChecker.CheckConnection(getContext()))
            {
                if(recipe != null)
                {
                    ShowLoadingData();

                    GetRecipeStepList(recipe);
                    GetIngredientList(recipe);

                    return;
                }
            }
        }

        ShowNoInternetAccess();
    }

    private Recipe GetRecipeDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getParcelable(DataKey.RecipeDataKey);
    }

    private ArrayList<Ingredient> GetIngredientListDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getParcelableArrayList(DataKey.IngredientListKey);
    }

    private ArrayList<RecipeStep> GetRecipeStepListDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getParcelableArrayList(DataKey.RecipeStepListKey);
    }

    private boolean CheckNetworkStateDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getBoolean(DataKey.InternetAccessStateKey);
    }

    private boolean GetNetworkStateDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getBoolean(DataKey.InternetAccessStateKey);
    }

    private Recipe GetDataFromIntent(Bundle bundle)
    {
        if(bundle != null)
        {
            if(bundle.containsKey(DataKey.RecipeDataKey))
            {
                return bundle.getParcelable(DataKey.RecipeDataKey);
            }
        }

        return null;
    }

    private void GetRecipeStepList(Recipe recipe)
    {
        NetworkDataGetterSyncTask<ArrayList<RecipeStep>> networkDataGetterSyncTask = new NetworkDataGetterSyncTask<>
                (new RecipeStepListJSONParser(recipe.getId()), new RecipeStepListTaskCompleteListener());
        networkDataGetterSyncTask.execute(recipeListURL);
    }

    private void GetIngredientList(Recipe recipe)
    {
        NetworkDataGetterSyncTask<ArrayList<Ingredient>> networkDataGetterSyncTask = new NetworkDataGetterSyncTask<>
                (new IngredientListJSONParser(recipe.getId()), new IngredientListTaskCompleteListener());
        networkDataGetterSyncTask.execute(recipeListURL);
    }

    private void SetIngredientRecyclerView(ArrayList<Ingredient> ingredientArrayList)
    {
        ingredientRecyclerView.setAdapter(new IngredientListAdapter(ingredientArrayList));
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setNestedScrollingEnabled(false);
    }

    private void SetRecipeStepRecyclerView(ArrayList<RecipeStep> recipeStepArrayList)
    {
        RecipeStepListAdapter recipeStepListAdapter;

        if(recipeStepListOnSelectedListener != null)
        {
            recipeStepListAdapter = new RecipeStepListAdapter(recipeStepArrayList, recipeStepListOnSelectedListener);
        }

        else
        {
            recipeStepListAdapter = new RecipeStepListAdapter(recipeStepArrayList, new DefaultPhoneRecipeStepListOnSelectedListener(getContext()));
        }

        recipeStepRecyclerView.setAdapter(recipeStepListAdapter);
        recipeStepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeStepRecyclerView.setHasFixedSize(true);
    }

    private void CheckDataHasBeenLoaded()
    {
        if(dataHasBeenLoad)
        {
            recipeDetailRootLayout.setVisibility(View.VISIBLE);
            loadingDataLayout.setVisibility(View.GONE);
            noInternetAccessLayout.setVisibility(View.GONE);
        }

        else
        {
            dataHasBeenLoad = true;
        }
    }

    private void ShowLoadingData()
    {
        recipeDetailRootLayout.setVisibility(View.GONE);
        loadingDataLayout.setVisibility(View.VISIBLE);
        noInternetAccessLayout.setVisibility(View.GONE);

        dataHasBeenLoad = false;
    }

    private void ShowNoInternetAccess()
    {
        recipeDetailRootLayout.setVisibility(View.GONE);
        loadingDataLayout.setVisibility(View.GONE);
        noInternetAccessLayout.setVisibility(View.VISIBLE);
    }

    private class RecipeStepListTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<RecipeStep>>
    {
        @Override
        public void onComplete(ArrayList<RecipeStep> recipeSteps) {

            if(recipeSteps != null)
            {
                recipeStepArrayList = recipeSteps;
                SetRecipeStepRecyclerView(recipeStepArrayList);
                CheckDataHasBeenLoaded();

                internetAccessState = true;
            }

            else
            {
                ShowNoInternetAccess();

                internetAccessState = false;
            }
        }
    }

    private class IngredientListTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Ingredient>>
    {
        @Override
        public void onComplete(ArrayList<Ingredient> ingredients) {

            if(ingredients != null)
            {
                ingredientArrayList = ingredients;
                SetIngredientRecyclerView(ingredientArrayList);
                CheckDataHasBeenLoaded();

                internetAccessState = true;
            }

            else
            {
                ShowNoInternetAccess();

                internetAccessState = false;
            }
        }
    }

    private class CardViewOnClickListener implements CardView.OnClickListener
    {
        @Override
        public void onClick(View v) {

            TransitionManager.beginDelayedTransition(recipeDetailRootLayout);

            if(expandState)
            {
                ingredientRecyclerView.setVisibility(View.GONE);
            }

            else
            {
                ingredientRecyclerView.setVisibility(View.VISIBLE);
            }

            expandState = !expandState;
        }
    }

    private class TryAgainClickListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {

            ShowLoadingData();

            GetRecipeStepList(recipe);
            GetIngredientList(recipe);
        }
    }
}
