package com.example.android.bakingapps.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapps.Data.DataKey;
import com.example.android.bakingapps.Data.RecipeStep;
import com.example.android.bakingapps.ExoPlayer.MyDefaultHttpExoExoPlayer;
import com.example.android.bakingapps.ExoPlayer.MyExoPlayer;
import com.example.android.bakingapps.R;
import com.example.android.bakingapps.eventHandler.DefaultPhoneRecipeStepListOnSelectedListener;
import com.example.android.bakingapps.eventHandler.RecipeStepListOnSelectedListener;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class RecipeStepDetailFragment extends Fragment
{
    MyExoPlayer defaultHttpPlayer;
    ArrayList<RecipeStep> recipeStepArrayList;
    int stepNumber;
    RecipeStepListOnSelectedListener recipeStepListOnSelectedListener;

    SimpleExoPlayerView exoPlayerView;
    TextView recipeNameTextView;
    TextView recipeNumberTextView;
    TextView recipeDescriptionTextView;
    Button previousStepButton;
    Button nextStepButton;
    ImageView recipeStepImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_step_detail_layout, container, false);

        exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.expv_recipe_step);
        recipeNameTextView = (TextView) view.findViewById(R.id.txt_recipe_step_name);
        recipeNumberTextView = (TextView) view.findViewById(R.id.txt_recipe_step_number);
        recipeDescriptionTextView = (TextView) view.findViewById(R.id.txt_recipe_step_description);
        previousStepButton = (Button) view.findViewById(R.id.btn_previous_recipe_step);
        nextStepButton = (Button) view.findViewById(R.id.btn_next_recipe_step);
        recipeStepImage = (ImageView) view.findViewById(R.id.iv_recipe_step_image);

        defaultHttpPlayer = new MyDefaultHttpExoExoPlayer(exoPlayerView, getContext());

        SetInitialData(savedInstanceState);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(Util.SDK_INT > 23 && recipeStepArrayList != null)
        {
            if(!recipeStepArrayList.get(stepNumber).getVideoURL().isEmpty())
            {
                defaultHttpPlayer.InitializePlayer(recipeStepArrayList.get(stepNumber).getVideoURL());
                ShowExoPlayer();

                if(recipeDescriptionTextView == null)
                {
                    defaultHttpPlayer.hideSystemUI();
                }
            }

            else
            {


                if(!recipeStepArrayList.get(stepNumber).getThumbnailURL().isEmpty())
                {
                    Picasso.with(getContext()).load(recipeStepArrayList.get(stepNumber).getThumbnailURL())
                            .error(R.drawable.ic_error_outline_black_48dp)
                            .placeholder(R.drawable.ic_cached_black_48dp)
                            .into(recipeStepImage);

                    ShowRecipeStepImage();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if((Util.SDK_INT <= 23 || defaultHttpPlayer.getExoPlayer() == null) && recipeStepArrayList != null)
        {
            if(!recipeStepArrayList.get(stepNumber).getVideoURL().isEmpty())
            {
                defaultHttpPlayer.InitializePlayer(recipeStepArrayList.get(stepNumber).getVideoURL());
                ShowExoPlayer();

                if(recipeDescriptionTextView == null)
                {
                    defaultHttpPlayer.hideSystemUI();
                }
            }

            else
            {
                if(!recipeStepArrayList.get(stepNumber).getThumbnailURL().isEmpty())
                {
                    Picasso.with(getContext()).load(recipeStepArrayList.get(stepNumber).getThumbnailURL())
                            .error(R.drawable.ic_error_outline_black_48dp)
                            .placeholder(R.drawable.ic_cached_black_48dp)
                            .into(recipeStepImage);

                    ShowRecipeStepImage();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(Util.SDK_INT <= 23)
        {
            defaultHttpPlayer.ReleasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if(Util.SDK_INT > 23)
        {
            defaultHttpPlayer.ReleasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!recipeStepArrayList.get(stepNumber).getVideoURL().isEmpty())
        {
            outState.putLong(DataKey.PlayBackPositionKey, defaultHttpPlayer.getPlayBackPosition());
            outState.putInt(DataKey.CurrentWindowKey, defaultHttpPlayer.getCurrentWindow());
            outState.putBoolean(DataKey.PlayWhenReadyKey, defaultHttpPlayer.isPlayWhenReady());
        }

        outState.putParcelableArrayList(DataKey.RecipeStepListKey, recipeStepArrayList);
        outState.putInt(DataKey.NumberStepKey, stepNumber);
    }

    public void setRecipeStepListOnSelectedListener(RecipeStepListOnSelectedListener recipeStepListOnSelectedListener) {
        this.recipeStepListOnSelectedListener = recipeStepListOnSelectedListener;
    }

    private void SetInitialData(Bundle savedInstanceState)
    {
        if(savedInstanceState != null)
        {
            if(savedInstanceState.containsKey(DataKey.PlayBackPositionKey) || savedInstanceState.containsKey(DataKey.CurrentWindowKey) || savedInstanceState.containsKey(DataKey.PlayWhenReadyKey))
            {
                long playBackPosition = savedInstanceState.getLong(DataKey.PlayBackPositionKey);
                int currentWindow = savedInstanceState.getInt(DataKey.CurrentWindowKey);
                boolean playWhenReady = savedInstanceState.getBoolean(DataKey.PlayWhenReadyKey);

                defaultHttpPlayer.SetPlayerStateAndPosition(playBackPosition, currentWindow, playWhenReady);
            }

            recipeStepArrayList = savedInstanceState.getParcelableArrayList(DataKey.RecipeStepListKey);
            stepNumber = savedInstanceState.getInt(DataKey.NumberStepKey);
        }

        else
        {
            recipeStepArrayList = GetRecipeDetailListFromIntent(getArguments());
            stepNumber = GetRecipeNumberFromIntent(getArguments());
        }

        if(recipeNameTextView != null)
        {
            SetView(recipeStepArrayList.get(stepNumber), stepNumber);
        }
    }

    private void SetView(RecipeStep recipeStep, final int stepNumber)
    {
        if(recipeStepListOnSelectedListener == null)
        {
            recipeStepListOnSelectedListener = new DefaultPhoneRecipeStepListOnSelectedListener(getContext());
        }

        if(recipeStep != null)
        {
            recipeNameTextView.setText(recipeStep.getName());
            recipeNumberTextView.setText("Step " + String.valueOf(stepNumber));
            recipeDescriptionTextView.setText(recipeStep.getDescription());

            if(stepNumber == 0)
            {
                previousStepButton.setClickable(false);
                previousStepButton.setText("");
            }

            else
            {
                previousStepButton.setClickable(true);
                previousStepButton.setText(getString(R.string.previous_button_label));
                previousStepButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recipeStepListOnSelectedListener.OnRecipeStepSelected(recipeStepArrayList, stepNumber - 1);
                    }
                });
//                previousStepButton.setOnClickListener(new RecipeStepListOnSelectedListener(recipeStepArrayList, stepNumber - 1, getContext()));
            }

            if(stepNumber == recipeStepArrayList.size() - 1)
            {
                nextStepButton.setClickable(false);
                nextStepButton.setText("");
            }

            else
            {
                nextStepButton.setClickable(true);
                nextStepButton.setText(getString(R.string.next_button_label));
                nextStepButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recipeStepListOnSelectedListener.OnRecipeStepSelected(recipeStepArrayList, stepNumber + 1);
                    }
                });
//                nextStepButton.setOnClickListener(new RecipeStepListOnSelectedListener(recipeStepArrayList, stepNumber + 1, getContext()));
            }
        }
    }

    private ArrayList<RecipeStep> GetRecipeDetailListFromIntent(Bundle bundle)
    {
        if(bundle != null)
        {
            if(bundle.containsKey(DataKey.RecipeStepListKey))
            {
                return bundle.getParcelableArrayList(DataKey.RecipeStepListKey);
            }
        }

        return null;
    }

    private int GetRecipeNumberFromIntent(Bundle bundle)
    {
        if(bundle != null)
        {
            if(bundle.containsKey(DataKey.NumberStepKey))
            {
                return bundle.getInt(DataKey.NumberStepKey, 1);
            }
        }

        return  1;
    }

    private void ShowRecipeStepImage()
    {
        exoPlayerView.setVisibility(View.GONE);
        recipeStepImage.setVisibility(View.VISIBLE);
    }

    private void ShowExoPlayer()
    {
        exoPlayerView.setVisibility(View.VISIBLE);
        recipeStepImage.setVisibility(View.GONE);
    }

}
