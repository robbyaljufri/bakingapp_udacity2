package com.example.android.bakingapps.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapps.Activity.RecipeDetailForTabletActivity;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.eventHandler.RecipeStepListOnSelectedListener;

import java.util.ArrayList;

/**
 * Created by robby on 14/08/17.
 */

public class RecipeStepListViewHolder extends RecyclerView.ViewHolder
{
    ArrayList<RecipeStep> recipeStepArrayList;
    RecipeStepListOnSelectedListener recipeStepListOnSelectedListener;

    TextView numberOfRecipeStepTextView;
    TextView recipeStepNameTextView;
    View view;
    Context context;

   public RecipeStepListViewHolder(View itemView, ArrayList<RecipeStep> recipeStepArrayList, Context context, RecipeStepListOnSelectedListener recipeStepListOnSelectedListener) {
       super(itemView);
       this.recipeStepArrayList = recipeStepArrayList;
       this.recipeStepListOnSelectedListener = recipeStepListOnSelectedListener;
       this.view = itemView;
       this.context = context;

       recipeStepNameTextView = (TextView) itemView.findViewById(R.id.txt_recipe_step_name);
       numberOfRecipeStepTextView = (TextView) itemView.findViewById(R.id.txt_recipe_step_number);
    }

    public void Bind(final int position)
    {
        RecipeStep recipeStep = recipeStepArrayList.get(position);

        recipeStepNameTextView.setText(recipeStep.getName());
        numberOfRecipeStepTextView.setText(String.valueOf(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeStepListOnSelectedListener.OnRecipeStepSelected(recipeStepArrayList, position);
            }
        });
    }
}
