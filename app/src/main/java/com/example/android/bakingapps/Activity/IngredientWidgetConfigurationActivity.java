package com.example.android.bakingapps.Activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.bakingapps.Adapter.RecipeListAdapter;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Fragment.RecipeDetailFragment;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.Widget.IngredientListRemoteViewService;
import com.example.android.bakingapps.Widget.IngredientListWidget;
import com.example.android.bakingapps.Widget.WidgetConfigurationService;
import com.example.android.bakingapps.asyncTask.AsyncTaskCompleteListener;
import com.example.android.bakingapps.asyncTask.NetworkDataGetterSyncTask;
import com.example.android.bakingapps.databinding.ActivityRecipeListBinding;
import com.example.android.bakingapps.eventHandler.DefaultRecipeListOnSelectedListener;
import com.example.android.bakingapps.eventHandler.RecipeListOnSelectedListener;
import com.example.android.bakingapps.jsonParsing.IngredientListJSONParser;
import com.example.android.bakingapps.jsonParsing.RecipeListJSONParser;
import com.example.android.bakingapps.localDatabase.DataDB;
import com.example.android.bakingapps.localDatabase.IngredientDataDB;
import com.example.android.bakingapps.localDatabase.RecipeDataDB;

import java.util.ArrayList;

public class IngredientWidgetConfigurationActivity extends AppCompatActivity {

    ActivityRecipeListBinding recipeListBinding;
    private int mAppWidgetId = 0 ;
    Recipe recipe;
    String recipeListURL = "http://go.udacity.com/android-baking-app-json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeListBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null)
        {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            getRecipeList();
        }

    }

    private void ShowLoadingData()
    {
        recipeListBinding.rvRecipeList.setVisibility(View.GONE);
        recipeListBinding.loadingDataLayout.setVisibility(View.VISIBLE);
        recipeListBinding.noInternetAccessLayout.setVisibility(View.GONE);
    }

    private void ShowRecyclerView()
    {
        recipeListBinding.rvRecipeList.setVisibility(View.VISIBLE);
        recipeListBinding.loadingDataLayout.setVisibility(View.GONE);
        recipeListBinding.noInternetAccessLayout.setVisibility(View.GONE);
    }

    private void ShowNoInternetAccess()
    {
        recipeListBinding.rvRecipeList.setVisibility(View.GONE);
        recipeListBinding.loadingDataLayout.setVisibility(View.GONE);
        recipeListBinding.noInternetAccessLayout.setVisibility(View.VISIBLE);
    }

    private void SetRecipeRecyclerView(ArrayList<Recipe> recipes)
    {
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(recipes, new IngredientWidgetOnSelectedListener());

        recipeListBinding.rvRecipeList.setLayoutManager(new LinearLayoutManager(this));
        recipeListBinding.rvRecipeList.setAdapter(recipeListAdapter);
        recipeListBinding.rvRecipeList.setHasFixedSize(true);
    }

    private void getRecipeList()
    {
        ShowLoadingData();

        NetworkDataGetterSyncTask<ArrayList<Recipe>> networkDataGetterSyncTask = new NetworkDataGetterSyncTask<>(new RecipeListJSONParser(), new RecipeListAsyncTaskCompleteListener());
        networkDataGetterSyncTask.execute(recipeListURL);

        /*DataGetterAsyncTask<Recipe> dataGetterAsyncTask = new DataGetterAsyncTask<Recipe>(new RecipeListJSONParser(), new RecipeListAsyncTaskCompleteListener());
        dataGetterAsyncTask.execute(recipeListURL);*/
    }

    private void GetIngredientList(Recipe recipe)
    {
        NetworkDataGetterSyncTask<ArrayList<Ingredient>> networkDataGetterSyncTask = new NetworkDataGetterSyncTask<>
                (new IngredientListJSONParser(recipe.getId()), new IngredientListTaskCompleteListener());
        networkDataGetterSyncTask.execute(recipeListURL);
    }

    public class IngredientWidgetOnSelectedListener implements RecipeListOnSelectedListener
    {
        @Override
        public void OnRecipeSelected(Recipe recipe)
        {
            IngredientWidgetConfigurationActivity.this.recipe = recipe;
            ShowLoadingData();

            GetIngredientList(recipe);
        }
    }

    private class RecipeListAsyncTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Recipe>>
    {
        @Override
        public void onComplete(ArrayList<Recipe> recipes) {

            if(recipes != null)
            {
                SetRecipeRecyclerView(recipes);
                ShowRecyclerView();
            }

            else
            {
                finish();
            }
        }
    }

    private class IngredientListTaskCompleteListener implements AsyncTaskCompleteListener<ArrayList<Ingredient>>
    {
        @Override
        public void onComplete(ArrayList<Ingredient> ingredients) {

            if(ingredients != null)
            {
                /*RemoteViews views = new RemoteViews(getPackageName(), R.layout.ingredient_list_widget);
                views.setTextViewText(R.id.txt_recipeName_ingredient_widget, recipe.getName());

                Intent intent = new Intent(getApplicationContext(), IngredientListRemoteViewService.class);
                intent.putExtra(DataKey.IngredientListKey, ingredients);
                views.setRemoteAdapter(R.id.listview_ingredient_widget, intent);
                views.setEmptyView(R.id.listview_ingredient_widget, R.id.empty_view);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(IngredientWidgetConfigurationActivity.this);
                appWidgetManager.updateAppWidget(mAppWidgetId, views);*/

                DataDB<Recipe> recipeDataDB = new RecipeDataDB(IngredientWidgetConfigurationActivity.this);
                recipeDataDB.removeAllData();
                recipeDataDB.addData(recipe);

                DataDB<Ingredient> ingredientDataDB = new IngredientDataDB(IngredientWidgetConfigurationActivity.this);
                ingredientDataDB.removeAllData();

                for (Ingredient ingredient:ingredients)
                {
                    ingredientDataDB.addData(ingredient);
                }

                WidgetConfigurationService.startConfigureWidget(IngredientWidgetConfigurationActivity.this);

                Intent resultIntent = new Intent();
                resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultIntent);
            }

            finish();
        }
    }



}
