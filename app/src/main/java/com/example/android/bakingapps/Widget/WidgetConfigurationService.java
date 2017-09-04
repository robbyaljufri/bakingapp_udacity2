package com.example.android.bakingapps.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;

import com.example.android.bakingapps.Activity.IngredientWidgetConfigurationActivity;
import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.localDatabase.DataDB;
import com.example.android.bakingapps.localDatabase.RecipeDataDB;

import java.util.ArrayList;

public class WidgetConfigurationService extends IntentService {


    public WidgetConfigurationService() {
        super("WidgetConfigurationService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

        DataDB<Recipe> recipeDataDB = new RecipeDataDB(getApplicationContext());
        ArrayList<Recipe> recipeArrayList = recipeDataDB.getAllData();

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.ingredient_list_widget);
        views.setTextViewText(R.id.txt_recipeName_ingredient_widget, recipeArrayList.get(0).getName());

        Intent remoteAdapterIntent = new Intent(this, IngredientListRemoteViewService.class);
        views.setRemoteAdapter(R.id.listview_ingredient_widget, remoteAdapterIntent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientListWidget.class));
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }


    public static void startConfigureWidget(Context context)
    {
        Intent intent = new Intent(context, WidgetConfigurationService.class);
        context.startService(intent);
    }
}
