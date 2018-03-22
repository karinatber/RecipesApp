package com.android.project3.recipesapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.data.Step;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements DetailsFragment.OnStepClickedInterface, StepVideoFragment.SwitchRecipeListener{
    final static String TAG = RecipeDetailActivity.class.getSimpleName();

    Recipe mRecipeData;
    int mStatus;
    int mStepId;

    final static String RECIPE_DETAILS_STATUS_KEY = "recipe-detail-status";
    final static String STEP_ID_KEY = "step-id";
    final static String RECIPE_DATA_KEY = "recipe-data";

    final static int DETAILS_STATUS = 0;
    final static int STEP_STATUS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(savedInstanceState != null){
            mStatus = savedInstanceState.getInt(RECIPE_DETAILS_STATUS_KEY);
            mStepId = savedInstanceState.getInt(STEP_ID_KEY);
            mRecipeData = savedInstanceState.getParcelable(RECIPE_DATA_KEY);
        } else {
            Intent originIntent = getIntent();
            mRecipeData = originIntent.getParcelableExtra(DetailsFragment.EXTRA_RECIPE);
            setTitle(mRecipeData.getName());
            mStatus = DETAILS_STATUS;
        }
        FragmentManager manager = getSupportFragmentManager();
        if (mStatus == DETAILS_STATUS) {
            /**creating ingredients list**/
            DetailsFragment ingrFragment = new DetailsFragment();
            ingrFragment.setRecipeData(mRecipeData, DetailsFragment.INGREDIENTS_LABEL);
            manager.beginTransaction()
                    .add(R.id.ingredients_container, ingrFragment)
                    .commit();

            /**creating steps list**/
            DetailsFragment stepsFragment = new DetailsFragment();
            stepsFragment.setRecipeData(mRecipeData, DetailsFragment.STEPS_LABEL);
            manager.beginTransaction()
                    .add(R.id.steps_container, stepsFragment)
                    .commit();
        } else if (mStatus == STEP_STATUS){
            Step step = mRecipeData.getSteps().get(mStepId);
            StepVideoFragment stepVideoFragment = new StepVideoFragment();
            stepVideoFragment.setStepListAndPosition(mRecipeData.getSteps(), mStepId);

            FrameLayout stepsContainer = (FrameLayout)findViewById(R.id.steps_container);
            TextView mTopTextView = (TextView)findViewById(R.id.tv_top_subtitle);
            mTopTextView.setText(step.getShortDescription());

            TextView mDownTextView = (TextView)findViewById(R.id.tv_down_subtitle);
            mDownTextView.setVisibility(View.INVISIBLE);

            stepsContainer.setVisibility(View.INVISIBLE);

            manager.beginTransaction()
                    .add(R.id.ingredients_container, stepVideoFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_DETAILS_STATUS_KEY, mStatus);
        outState.putInt(STEP_ID_KEY, mStepId);
        outState.putParcelable(RECIPE_DATA_KEY, mRecipeData);
    }

    @Override
    public void onStepChosen(int stepId) {
        mStepId = stepId;
        List<Step> stepsList = mRecipeData.getSteps();
        showStepDetails(stepsList.get(stepId));
    }

    private void showStepDetails(Step step){
        mStatus = STEP_STATUS;
        FragmentManager manager = getSupportFragmentManager();
        StepVideoFragment stepVideoFragment = new StepVideoFragment();
        stepVideoFragment.setStepListAndPosition(mRecipeData.getSteps(), mStepId);

        FrameLayout stepsContainer = (FrameLayout)findViewById(R.id.steps_container);
        TextView mTopTextView = (TextView)findViewById(R.id.tv_top_subtitle);
        mTopTextView.setText(step.getShortDescription());

        TextView mDownTextView = (TextView)findViewById(R.id.tv_down_subtitle);
        mDownTextView.setVisibility(View.INVISIBLE);

        stepsContainer.setVisibility(View.INVISIBLE);

        manager.beginTransaction()
                .replace(R.id.ingredients_container, stepVideoFragment)
                .commit();
    }

    private void showRecipeDetails(){
        mStatus = DETAILS_STATUS;
        FragmentManager manager = getSupportFragmentManager();
        /**creating ingredients list**/
        DetailsFragment ingrFragment = new DetailsFragment();
        ingrFragment.setRecipeData(mRecipeData, DetailsFragment.INGREDIENTS_LABEL);
        manager.beginTransaction()
                .replace(R.id.ingredients_container, ingrFragment)
                .commit();
        FrameLayout stepsContainer = (FrameLayout)findViewById(R.id.steps_container);
        stepsContainer.setVisibility(View.VISIBLE);

        /**creating steps list**/
        DetailsFragment stepsFragment = new DetailsFragment();
        stepsFragment.setRecipeData(mRecipeData, DetailsFragment.STEPS_LABEL);
        manager.beginTransaction()
                .replace(R.id.steps_container, stepsFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        switch(mStatus){
            case DETAILS_STATUS:
                super.onBackPressed();
                break;
            case STEP_STATUS:
                showRecipeDetails();
                break;
        }
    }

    @Override
    public void onRecipeChange(int position) {
        Log.v(TAG, "onRecipeChange was called");
        mStepId = position;
        showStepDetails(mRecipeData.getSteps().get(position));
    }
}
