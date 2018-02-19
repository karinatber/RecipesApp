package com.android.project3.recipesapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    public RecipesListFragment(){
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
        service.executeService();
    }


    @Override
    public void onRecipeClick(Recipe recipe) {
        Toast.makeText(this.getContext(), "Recipe: "+recipe.getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExecFinished(List<Recipe> recipes) {
        mRecipeList = recipes;
        mAdapter.setRecipesList(recipes);
    }
}
