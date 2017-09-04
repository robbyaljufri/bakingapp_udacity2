package com.example.android.bakingapps.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.R;
//import com.example.android.bakingapps.eventHandler.RecipeListInTabletClickListener;
import com.example.android.bakingapps.eventHandler.RecipeListOnSelectedListener;
import com.squareup.picasso.Picasso;

/**
 * Created by robby on 15/08/17.
 */

public class RecipeListViewHolder extends RecyclerView.ViewHolder
{
    TextView recipeNameTextView;
    TextView recipeInfoTextView;
    Button recipeDetailButton;
    ImageView recipeImageView;

    Context context;
    RecipeListOnSelectedListener recipeListOnSelectedListener;
//    RecipeListInTabletClickListener recipeListInTabletClickListener;

    public RecipeListViewHolder(View itemView, Context context, RecipeListOnSelectedListener recipeListOnSelectedListener) {
        super(itemView);
        this.context = context;
        this.recipeListOnSelectedListener = recipeListOnSelectedListener;

        recipeNameTextView = (TextView) itemView.findViewById(R.id.txt_recipe_name);
        recipeInfoTextView =(TextView) itemView.findViewById(R.id.txt_recipe_information);
        recipeDetailButton = (Button) itemView.findViewById(R.id.btn_view_detail_recipe);
        recipeImageView = (ImageView) itemView.findViewById(R.id.iv_recipe_image);
    }

    public void Bind(final Recipe recipe)
    {
        String recipeInfo = recipe.getServing() + " Servings";

        recipeNameTextView.setText(recipe.getName());
        recipeInfoTextView.setText(recipeInfo);

        if(recipe.getImageURL() != null)
        {
            if(!recipe.getImageURL().isEmpty())
            {
                Picasso.with(context).
                        load(recipe.getImageURL()).
                        placeholder(R.drawable.ic_cached_black_48dp).
                        error(R.drawable.ic_error_outline_black_48dp).
                        into(recipeImageView);
            }
        }

        recipeDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeListOnSelectedListener.OnRecipeSelected(recipe);
            }
        });

    }


}
