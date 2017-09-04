package com.example.android.bakingapps.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.R;
//import com.example.android.bakingapps.eventHandler.RecipeListInTabletClickListener;
import com.example.android.bakingapps.eventHandler.RecipeListOnSelectedListener;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

/**
 * Created by robby on 15/08/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder>
{
    ArrayList<Recipe> recipeArrayList;
    RecipeListOnSelectedListener recipeListOnSelectedListener;


    public RecipeListAdapter(ArrayList<Recipe> recipeArrayList, RecipeListOnSelectedListener recipeListOnSelectedListener) {
        this.recipeArrayList = recipeArrayList;
        this.recipeListOnSelectedListener = recipeListOnSelectedListener;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_list, parent, false);
        return new RecipeListViewHolder(view, parent.getContext(), recipeListOnSelectedListener);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        holder.Bind(recipeArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }
}
