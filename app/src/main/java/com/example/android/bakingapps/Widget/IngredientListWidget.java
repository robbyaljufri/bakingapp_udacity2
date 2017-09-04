package com.example.android.bakingapps.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.R;

import java.util.ArrayList;


public class IngredientListWidget extends AppWidgetProvider {

    ArrayList<Ingredient> ingredientArrayList;
    Recipe recipe;

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        /*// Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_widget);
        views.setTextViewText(R.id.txt_recipeName_ingredient_widget, recipe.getName());

        Intent intent = new Intent(context, IngredientListRemoteViewService.class);
        intent.putExtra(DataKey.IngredientListKey, ingredientArrayList);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.listview_ingredient_widget, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);*/
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        /*for (int id:appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, id);
        }*/

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

