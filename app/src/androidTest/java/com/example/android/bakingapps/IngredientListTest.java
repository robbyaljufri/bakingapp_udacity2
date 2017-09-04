package com.example.android.bakingapps;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapps.Activity.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by nugroho on 29/08/17.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IngredientListTest {

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

        onView(withId(R.id.cv_recipe_ingredients)).perform(click());

        onView(withRecyclerView(R.id.rv_ingredient_list).atPositionOnView(0, R.id.txt_name_ingredient))
                .check(matches(withText("Graham Cracker crumbs")));

        onView(withRecyclerView(R.id.rv_ingredient_list).atPositionOnView(0, R.id.txt_amount_of_ingredient))
                .check(matches(withText("2")));

        onView(withRecyclerView(R.id.rv_ingredient_list).atPositionOnView(0, R.id.txt_measure_ingredient))
                .check(matches(withText("CUP OF")));

        onView(withRecyclerView(R.id.rv_ingredient_list).atPositionOnView(1, R.id.txt_name_ingredient))
                .check(matches(withText("unsalted butter, melted")));

        onView(withId(R.id.cv_recipe_ingredients)).perform(click());
    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
