package com.example.android.bakingapps.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.R;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

/**
 * Created by robby on 14/08/17.
 */

public class IngredientListViewHolder extends RecyclerView.ViewHolder
{
    TextView nameIngredientTextView;
    TextView amountIngredientTextView;
    TextView measureIngredientTextView;

    public IngredientListViewHolder(View itemView)
    {
        super(itemView);

        nameIngredientTextView = (TextView) itemView.findViewById(R.id.txt_name_ingredient);
        amountIngredientTextView = (TextView) itemView.findViewById(R.id.txt_amount_of_ingredient);
        measureIngredientTextView = (TextView) itemView.findViewById(R.id.txt_measure_ingredient);
    }

    public void Bind(Ingredient ingredient)
    {
        nameIngredientTextView.setText(ingredient.getName());

        String measure =  ingredient.getMeasure() + " OF";
        measureIngredientTextView.setText(measure);

        double sisa_bagi = ingredient.getQuantity() % 1;
        String amount = String.valueOf(ingredient.getQuantity());

        if(sisa_bagi == 0)
        {
            String[] strings = amount.split(Pattern.quote("."));
            amount = strings[0];
        }

        amountIngredientTextView.setText(amount);

    }

}
