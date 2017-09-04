package com.example.android.bakingapps.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.localDatabase.DataDB;
import com.example.android.bakingapps.localDatabase.IngredientDataDB;

import java.util.ArrayList;


public class IngredientListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory
{
    Context context;
    DataDB<Ingredient> ingredientDataDB;
    ArrayList<Ingredient> ingredientArrayList;

    public IngredientListRemoteViewFactory(Context context) {
        this.context = context;
        ingredientDataDB = new IngredientDataDB(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        ingredientArrayList = ingredientDataDB.getAllData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_item_list_widget);

        remoteViews.setTextViewText(R.id.txt_name_ingredient, ingredientArrayList.get(position).getName());
        remoteViews.setTextViewText(R.id.txt_measure_ingredient, ingredientArrayList.get(position).getMeasure());
        remoteViews.setTextViewText(R.id.txt_amount_of_ingredient, String.valueOf(ingredientArrayList.get(position).getQuantity()));

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
