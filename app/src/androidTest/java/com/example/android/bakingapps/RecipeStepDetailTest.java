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

public class RecipeStepDetailTest {

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

        onView(withId(R.id.rv_recipe_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.txt_recipe_step_name)).check(matches(withText("Prep the cookie crust.")));
        onView(withId(R.id.txt_recipe_step_number)).check(matches(withText("Step 2")));
        onView(withId(R.id.txt_recipe_step_description)).check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));

        onView(withId(R.id.btn_next_recipe_step)).perform(click());

        onView(withId(R.id.txt_recipe_step_name)).check(matches(withText("Press the crust into baking form.")));
        onView(withId(R.id.txt_recipe_step_number)).check(matches(withText("Step 3")));
        onView(withId(R.id.txt_recipe_step_description)).check(matches(withText("3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.")));

        onView(withId(R.id.btn_previous_recipe_step)).perform(click());

        onView(withId(R.id.txt_recipe_step_name)).check(matches(withText("Prep the cookie crust.")));
        onView(withId(R.id.txt_recipe_step_number)).check(matches(withText("Step 2")));
        onView(withId(R.id.txt_recipe_step_description)).check(matches(withText("2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.")));

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}

