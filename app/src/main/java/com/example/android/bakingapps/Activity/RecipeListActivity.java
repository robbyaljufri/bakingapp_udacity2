package com.example.android.bakingapps.Activity;

import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapps.Adapter.RecipeListAdapter;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.asyncTask.AsyncTaskCompleteListener;
/*import com.example.android.bakingapps.asyncTask.DataGetterAsyncTask;
import com.example.android.bakingapps.asyncTask.DataListGetterAsyncTask;*/
import com.example.android.bakingapps.asyncTask.NetworkDataGetterSyncTask;
import com.example.android.bakingapps.databinding.ActivityRecipeListBinding;
import com.example.android.bakingapps.eventHandler.DefaultRecipeListOnSelectedListener;
//import com.example.android.bakingapps.eventHandler.RecipeListInTabletClickListener;
import com.example.android.bakingapps.jsonNetworkConnection.NetworkConnectionChecker;
import com.example.android.bakingapps.jsonParsing.IngredientListJSONParser;
import com.example.android.bakingapps.jsonParsing.RecipeListJSONParser;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class RecipeListActivity extends AppCompatActivity {
    
    Button btnTryAgain;
    ConstraintLayout noInternetAccessLayout;
    ConstraintLayout loadingDataLayout;
    RecyclerView rvRecipeList;
    ProgressBar pbLoadingData;
    TextView txtNoInternet;

    ArrayList<Recipe> recipeArrayList;
    String recipeListURL = "http://go.udacity.com/android-baking-app-json";

    boolean internetAccessState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        btnTryAgain = (Button) findViewById(R.id.btn_try_again);
        noInternetAccessLayout = (ConstraintLayout) findViewById(R.id.no_internet_access_layout);
        loadingDataLayout = (ConstraintLayout) findViewById(R.id.loading_data_layout);
        rvRecipeList = (RecyclerView) findViewById(R.id.rv_recipe_list);

        btnTryAgain.setOnClickListener(new TryAgainClickListener());
        SetInitialData(savedInstanceState);
    }

    private void SetInitialData(Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
        {
            if(CheckNetworkStateDataPersistance(savedInstanceState))
            {
                internetAccessState = GetNetworkStateDataPersistance(savedInstanceState);

                if(internetAccessState)
                {
                    recipeArrayList = GetRecipeListDataPersistance(savedInstanceState);
                    SetRecipeRecyclerView(recipeArrayList);
                    return;
                }
            }
        }

        else
        {
            if(NetworkConnectionChecker.CheckConnection(this))
            {
                getRecipeList();
                return;
            }
        }

        ShowNoInternetAccess();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(DataKey.InternetAccessStateKey, internetAccessState);

        if(recipeArrayList != null)
        {
            if(!recipeArrayList.isEmpty())
            {
                outState.putParcelableArrayList(DataKey.RecipeDataListKey, recipeArrayList);
            }
        }
    }

    private ArrayList<Recipe> GetRecipeListDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getParcelableArrayList(DataKey.RecipeDataListKey);
    }

    private boolean CheckNetworkStateDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getBoolean(DataKey.InternetAccessStateKey);
    }

    private boolean GetNetworkStateDataPersistance(Bundle savedInstanceState)
    {
        return savedInstanceState.getBoolean(DataKey.InternetAccessStateKey);
    }


    private void ShowLoadingData()
    {
        rvRecipeList.setVisibility(View.GONE);
        loadingDataLayout.setVisibility(View.VISIBLE);
        noInternetAccessLayout.setVisibility(View.GONE);
    }

    private void ShowRecyclerView()
    {
        rvRecipeList.setVisibility(View.VISIBLE);
        loadingDataLayout.setVisibility(View.GONE);
        noInternetAccessLayout.setVisibility(View.GONE);
    }

    private void ShowNoInternetAccess()
    {
        rvRecipeList.setVisibility(View.GONE);
        loadingDataLayout.setVisibility(View.GONE);
        noInternetAccessLayout.setVisibility(View.VISIBLE);
    }

    private void getRecipeList()
    {
        ShowLoadingData();

        NetworkDataGetterSyncTask<ArrayList<Recipe>> networkDataGetterSyncTask = new NetworkDataGetterSyncTask<>(new RecipeListJSONParser(), new RecipeListAsyncTaskCompleteListener());
        networkDataGetterSyncTask.execute(recipeListURL);
    }

    private void SetRecipeRecyclerView(ArrayList<Recipe> recipes)
    {
        RecipeListAdapter recipeListAdapter;

        if(getString(R.string.screen_type).equals("Tablet"))
        {
            recipeListAdapter = new RecipeListAdapter(recipes, new DefaultRecipeListOnSelectedListener(this, RecipeDetailForTabletActivity.class));
            rvRecipeList.setLayoutManager(new GridLayoutManager(this, 2));
        }

        else
        {
            recipeListAdapter = new RecipeListAdapter(recipes, new DefaultRecipeListOnSelectedListener(this, RecipeDetailActivity.class));
            rvRecipeList.setLayoutManager(new LinearLayoutManager(this));
        }

        rvRecipeList.setAdapter(recipeListAdapter);
        rvRecipeList.setHasFixedSize(true);
    }

    private class RecipeListAsyncTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>>
    {
        @Override
        public void onComplete(ArrayList<Recipe> recipes) {

            if(recipes == null)
            {
                ShowNoInternetAccess();

                internetAccessState = false;
            }

            else
            {
                recipeArrayList = recipes;
                SetRecipeRecyclerView(recipeArrayList);
                ShowRecyclerView();

                internetAccessState = true;
            }
        }
    }

    private class TryAgainClickListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {
            getRecipeList();
        }
    }
}
