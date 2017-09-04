package com.example.android.bakingapps.jsonParsing;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class IngredientListJSONParser implements JSONParser<ArrayList<Ingredient>>
{
    String idRecipe;

    public IngredientListJSONParser(String idRecipe) {
        this.idRecipe = idRecipe;
    }

    @Override
    public ArrayList<Ingredient> Parse(String jsonString) {

        try {

            int indexRecipe = Integer.parseInt(idRecipe);

            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject recipeJSON = jsonArray.getJSONObject(--indexRecipe);

            ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
            JSONArray ingredients = recipeJSON.getJSONArray("ingredients");

            for(int a = 0; a < ingredients.length(); a++)
            {
                JSONObject ingredient = ingredients.getJSONObject(a);

                int quantityIngredient = ingredient.getInt("quantity");
                String measureIngredient = ingredient.getString("measure");
                String nameIngredient = ingredient.getString("ingredient");

                ingredientArrayList.add(new Ingredient(nameIngredient, measureIngredient, quantityIngredient, idRecipe));
            }

            return ingredientArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
