package com.android.project3.recipesapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karinatber.project3.recipesapp.R;
import com.android.project3.recipesapp.adapter.IngredientsListAdapter;
import com.android.project3.recipesapp.adapter.StepsListAdapter;
import com.android.project3.recipesapp.data.Recipe;

/**
 * Created by katanbern on 21/02/2018.
 */

public class DetailsFragment extends Fragment implements StepsListAdapter.OnStepClickListener{
    public static final String TAG = DetailsFragment.class.getSimpleName();
    public static final int INGREDIENTS_LABEL = 0;
    public static final int STEPS_LABEL = 1;
    public static final String EXTRA_RECIPE = "recipe-data-key";

    RecyclerView mRecyclerView;
    Context mContext;
    Recipe mRecipeData;
    int mAdapterType;

    IngredientsListAdapter mIngrAdapter;
    StepsListAdapter mStepsAdapter;
    OnStepClickedInterface mOnStepClickedInterface;

    public DetailsFragment(){

    }

    public interface OnStepClickedInterface{
        void onStepChosen(int stepId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnStepClickedInterface = (OnStepClickedInterface)context;
        } catch(ClassCastException e){
            Log.e(TAG, "OnStepClickedInterface must be implemented");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_list_fragment, container, false);

        mContext = rootView.getContext();
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_recipes_list);
        if(mRecipeData != null) {
            createView();
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setRecipeData(Recipe recipeData, int adapterType){
        mRecipeData = recipeData;
        mAdapterType = adapterType;
    }

    private void createView(){
        switch (mAdapterType){
            case INGREDIENTS_LABEL:
                /**creating ingredients layout**/
                mIngrAdapter = new IngredientsListAdapter();
                mIngrAdapter.setIngredientsList(mRecipeData.getIngredients());
                LinearLayoutManager ingrManager = new LinearLayoutManager(mContext);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(ingrManager);
                mRecyclerView.setAdapter(mIngrAdapter);
                break;
            case STEPS_LABEL:
                /**creating steps layout**/
                mStepsAdapter = new StepsListAdapter(this);
                mStepsAdapter.setStepsList(mRecipeData.getSteps());
                LinearLayoutManager stepsManager = new LinearLayoutManager(mContext);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(stepsManager);
                mRecyclerView.setAdapter(mStepsAdapter);
                break;
        }
    }

    @Override
    public void onStepClick(int stepId) {
        String videoUrl = mRecipeData.getSteps().get(stepId).getVideoURL();
        if (videoUrl != null && !videoUrl.isEmpty()) {
            mOnStepClickedInterface.onStepChosen(stepId);
        }
    }
}
