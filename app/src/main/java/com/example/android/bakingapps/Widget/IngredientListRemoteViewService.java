package com.example.android.bakingapps.Widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.Ingredient;

import java.util.ArrayList;



public class IngredientListRemoteViewService extends RemoteViewsService
{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new IngredientListRemoteViewFactory(this.getApplicationContext());
    }
}
