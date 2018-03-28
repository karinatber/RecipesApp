package com.android.project3.recipesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.service.BaseService;

import java.util.List;

/**
 * Created by katanbern on 28/03/2018.
 */

public class RecipesAppUtils implements BaseService.OnApiServiceListener{
    private List<Recipe> mRecipeList;
    private Context mContext;

    public RecipesAppUtils(Context context){
        mContext = context;
        BaseService service = new BaseService(this);
        service.executeService(mContext, BaseService.ACTION_LOAD_ALL_RECIPES);
    }

    public List<Recipe> loadAllRecipes(){
        return mRecipeList;
    }

    public Recipe loadRecipeByID(){
        String preferenceKey = mContext.getString(R.string.PREFERENCE_RECIPE_KEY);
        SharedPreferences sp = mContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        int recipeID = sp.getInt(preferenceKey, 99);

        for (Recipe recipe : mRecipeList){
            if (recipe.getId() == recipeID){
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void onExecFinished(List<Recipe> recipes) {
        mRecipeList = recipes;
    }
}
