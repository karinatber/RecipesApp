package com.android.project3.recipesapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.data.Step;

public class RecipeDetailActivity extends AppCompatActivity implements DetailsFragment.OnStepClickedInterface{
    Recipe mRecipeData;
    int mStatus;

    final static int DETAILS_STATUS = 0;
    final static int STEP_STATUS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent originIntent = getIntent();
        mRecipeData = originIntent.getParcelableExtra(DetailsFragment.EXTRA_RECIPE);
        setTitle(mRecipeData.getName());

        mStatus = DETAILS_STATUS;
        FragmentManager manager = getSupportFragmentManager();
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
    }

    @Override
    public void onStepChosen(Step step) {
        showStepDetails(step);
    }

    private void showStepDetails(Step step){
        mStatus = STEP_STATUS;
        FragmentManager manager = getSupportFragmentManager();
        StepVideoFragment stepVideoFragment = new StepVideoFragment();
        stepVideoFragment.setStepData(step);

        FrameLayout stepsContainer = (FrameLayout)findViewById(R.id.steps_container);
        TextView mTopTextView = (TextView)findViewById(R.id.tv_top_subtitle);
        mTopTextView.setText(step.getShortDescription());

        TextView mDownTextView = (TextView)findViewById(R.id.tv_down_subtitle);
        mDownTextView.setVisibility(View.GONE);

        stepsContainer.setVisibility(View.GONE);

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
}
