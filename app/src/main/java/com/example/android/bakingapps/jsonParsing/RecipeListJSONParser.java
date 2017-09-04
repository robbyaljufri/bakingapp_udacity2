package com.example.android.bakingapps.jsonParsing;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.Recipe;
import com.example.android.bakingapps.Data.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class RecipeListJSONParser implements JSONParser<ArrayList<Recipe>>
{
    @Override
    public ArrayList<Recipe> Parse(String jsonString)
    {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        try
        {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int a = 0; a < jsonArray.length(); a++)
            {
                JSONObject recipeJSON = jsonArray.getJSONObject(a);

                String id = recipeJSON.getString("id");
                String recipeName = recipeJSON.getString("name");
                int recipeServing = recipeJSON.getInt("servings");
                String recipeImage = recipeJSON.getString("image");

                Recipe recipe = new Recipe(id, recipeName, recipeServing, recipeImage);
                recipeArrayList.add(recipe);
            }

            return recipeArrayList;

        } catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
