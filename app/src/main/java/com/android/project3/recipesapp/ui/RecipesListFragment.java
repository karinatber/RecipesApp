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

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.adapter.RecipesListAdapter;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.service.BaseService;

import java.util.List;

/**
 * Created by katanbern on 19/02/2018.
 */

public class RecipesListFragment extends Fragment implements RecipesListAdapter.OnRecipeClickListener, BaseService.OnApiServiceListener{
    public static final String TAG = RecipesListFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    List<Recipe> mRecipeList;
    RecipesListAdapter mAdapter;

    OnSelectRecipeInterface mOnSelectRecipeInterface;

    public RecipesListFragment(){
    }


    public interface OnSelectRecipeInterface{
        void onRecipeSelected(int position, List<Recipe> recipes);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mOnSelectRecipeInterface = (OnSelectRecipeInterface)context;
        } catch (ClassCastException e){
            Log.e(TAG, "Must implement OnSelectRecipeInterface.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_list_fragment, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipes_list);

        loadRecipes();
        mAdapter = new RecipesListAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    public void loadRecipes(){
        BaseService service = new BaseService(this);
        service.executeService(getContext(), BaseService.ACTION_LOAD_ALL_RECIPES);
    }

    @Override
    public void onRecipeClick(Recipe recipe, int position) {
        mOnSelectRecipeInterface.onRecipeSelected(position, mRecipeList);
    }

    @Override
    public void onExecFinished(List<Recipe> recipes) {
        mRecipeList = recipes;
        mAdapter.setRecipesList(recipes);
    }
}
