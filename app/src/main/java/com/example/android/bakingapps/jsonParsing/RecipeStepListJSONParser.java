package com.example.android.bakingapps.jsonParsing;

import com.example.android.bakingapps.Data.Ingredient;
import com.example.android.bakingapps.Data.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RecipeStepListJSONParser implements JSONParser<ArrayList<RecipeStep>>
{
    String idRecipe;

    public RecipeStepListJSONParser(String idRecipe) {
        this.idRecipe = idRecipe;
    }

    @Override
    public ArrayList<RecipeStep> Parse(String jsonString) {

        try {

            int indexRecipe = Integer.parseInt(idRecipe);

            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject recipeJSON = jsonArray.getJSONObject(--indexRecipe);

            ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();
            JSONArray steps = recipeJSON.getJSONArray("steps");

            for(int c = 0; c < steps.length(); c++)
            {
                JSONObject step = steps.getJSONObject(c);

                String stepName = step.getString("shortDescription");
                String stepDescription = step.getString("description");
                String stepVideoURL = step.getString("videoURL");
                String stepImageURL = step.getString("thumbnailURL");

                recipeStepArrayList.add(new RecipeStep(stepName, stepDescription, stepVideoURL, stepImageURL, idRecipe));
            }

            return recipeStepArrayList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
