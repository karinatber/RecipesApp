package com.android.project3.recipesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.service.BaseService;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by katanbern on 28/03/2018.
 */

public class RecipesAppUtils implements BaseService.OnApiServiceListener{
    public static final String TAG = RecipesAppUtils.class.getSimpleName();
    private List<Recipe> mRecipeList;
    private Context mContext;
    private SharedPreferences mPreferences;

    public RecipesAppUtils(Context context){
        mContext = context;
        mPreferences = context.getSharedPreferences(context.getString(R.string.PREFERENCE_RECIPE_ID_KEY), Context.MODE_PRIVATE);
    }

    public List<Recipe> loadAllRecipes(){
        return mRecipeList;
    }

    public Recipe loadRecipeByID(){
        String preferenceKey = mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY);
        SharedPreferences sp = mContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        int recipeID = sp.getInt(preferenceKey, 99);

        if(mRecipeList != null){
            for (Recipe recipe : mRecipeList){
                if (recipe.getId() == recipeID){
                    return recipe;
                }
            }
        } else{
            loadRecipesFromJson();
        }
        return null;
    }

    @Override
    public void onExecFinished(List<Recipe> recipes) {
        mRecipeList = recipes;
    }

    public List<Recipe> loadRecipesFromJson(){
        String json = recipesToJson();
        return null;
    }

    public String recipesToJson(){
        String strRecipes = getAllRecipesFromPreferences();
        Gson gson = new Gson();
        try {
            String jsonRecipes = gson.toJson(strRecipes);
            Log.i(TAG, "recipesToJson was called, json generated: " + jsonRecipes);
            return jsonRecipes;
        } catch(Exception e){
            Log.i(TAG, "Gson could not generate json from recipes String");
        }
        return null;
    }

    //update stored values of all recipes in SharedPreferences
    public void updatePreferenceRecipesList(List<Recipe> recipeList){
        String allRecipes = recipeList.toString();

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(
                mContext.getString(R.string.PREFERENCE_RECIPES_LIST_KEY),
                allRecipes
        );
        editor.apply();
        Log.i(TAG, "Stored recipes in preferences, value: "+allRecipes);
    }

    private String getAllRecipesFromPreferences(){
        return mPreferences.getString(mContext.getString(R.string.PREFERENCE_RECIPES_LIST_KEY), "");
    }
}
