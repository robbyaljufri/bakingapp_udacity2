package com.example.android.bakingapps;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.android.bakingapps.Activity.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by nugroho on 29/08/17.
 */

public class RecipeStepListTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> activityTestRule = new ActivityTestRule<RecipeListActivity>(RecipeListActivity.class);

    /*@Rule
    public ActivityTestRule<RecipeDetailActivity> recipeDetailActivityTestRule = new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class);*/

    @Test
    public void getIngredientList() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.scrollToPosition(0));

        onView(withRecyclerView(R.id.rv_recipe_list).atPositionOnView(0, R.id.btn_view_detail_recipe)).perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withRecyclerView(R.id.rv_recipe_step_list).atPositionOnView(0, R.id.txt_recipe_step_name))
                .check(matches(withText("Recipe Introduction")));

        onView(withRecyclerView(R.id.rv_recipe_step_list).atPositionOnView(0, R.id.txt_recipe_step_number))
                .check(matches(withText("0")));

        onView(withRecyclerView(R.id.rv_recipe_step_list).atPositionOnView(1, R.id.txt_recipe_step_name))
                .check(matches(withText("Starting prep")));

        onView(withRecyclerView(R.id.rv_recipe_step_list).atPositionOnView(1, R.id.txt_recipe_step_number))
                .check(matches(withText("1")));
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}

