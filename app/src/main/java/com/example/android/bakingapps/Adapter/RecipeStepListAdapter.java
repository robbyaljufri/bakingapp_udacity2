package com.example.android.bakingapps.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapps.Activity.RecipeDetailForTabletActivity;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.eventHandler.RecipeStepListOnSelectedListener;

import java.util.ArrayList;

/**
 * Created by robby on 14/08/17.
 */

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListViewHolder>
{
    ArrayList<RecipeStep> recipeStepArrayList;
    RecipeStepListOnSelectedListener recipeStepListOnSelectedListener;

    public RecipeStepListAdapter(ArrayList<RecipeStep> recipeStepArrayList, RecipeStepListOnSelectedListener recipeStepListOnSelectedListener) {
        this.recipeStepArrayList = recipeStepArrayList;
        this.recipeStepListOnSelectedListener = recipeStepListOnSelectedListener;
    }

    @Override
    public RecipeStepListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item_list, parent, false);
        return new RecipeStepListViewHolder(view, recipeStepArrayList, parent.getContext(), recipeStepListOnSelectedListener);
    }

    @Override
    public void onBindViewHolder(RecipeStepListViewHolder holder, int position) {
        holder.Bind(position);
    }

    @Override
    public int getItemCount() {
        return recipeStepArrayList.size();
    }
}
