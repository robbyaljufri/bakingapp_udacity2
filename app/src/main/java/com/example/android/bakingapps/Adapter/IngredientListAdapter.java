package com.example.android.bakingapps.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.R;

import java.util.ArrayList;

/**
 * Created by robby on 14/08/17.
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListViewHolder>
{
    ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();

    public IngredientListAdapter(ArrayList<Ingredient> ingredientArrayList) {
        this.ingredientArrayList = ingredientArrayList;
    }

    @Override
    public IngredientListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item_list, parent, false);
        return new IngredientListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientListViewHolder holder, int position) {
        holder.Bind(ingredientArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }
}
