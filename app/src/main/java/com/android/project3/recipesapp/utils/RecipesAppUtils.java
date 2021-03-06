package com.android.project3.recipesapp.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.data.Step;
import com.android.project3.recipesapp.service.BaseService;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by katanbern on 28/03/2018.
 */

public class RecipesAppUtils implements BaseService.OnApiServiceListener{
    public static final String TAG = RecipesAppUtils.class.getSimpleName();

    public static final int INVALID_RECIPE_ID = 99;
    public static final String EXTRA_APPWIDGET_ID = "extra-appwidget-id";

    private List<Recipe> mRecipeList;
    private Context mContext;
    private SharedPreferences mPreferences;

    public RecipesAppUtils(Context context){
        mContext = context;
        mPreferences = context.getSharedPreferences(context.getString(R.string.PREFERENCE_RECIPE_ID_KEY), Context.MODE_PRIVATE);
    }

    public List<Recipe> loadAllRecipes(){
        BaseService service = new BaseService(this);
        service.executeService(mContext, BaseService.ACTION_LOAD_ALL_RECIPES);
        return mRecipeList;
    }

    public Recipe loadRecipeByID(int appWidgetId){
        String preferenceKey = mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY);
        SharedPreferences sp = mContext.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        int recipeID = sp.getInt(preferenceKey, INVALID_RECIPE_ID);

        if(mRecipeList != null){
            for (Recipe recipe : mRecipeList){
                if (recipe.getId() == recipeID){
                    return recipe;
                }
            }
        } else{
            return loadRecipeFromJson(appWidgetId);
        }
        return null;
    }

    @Override
    public void onExecFinished(List<Recipe> recipes) {
        mRecipeList = recipes;
    }

    public Recipe loadRecipeFromJson(int appWidgetId){
        String json = getRecipeStrFromPreferences(appWidgetId);
        Log.i(TAG, "Loaded json from preferences: "+json);
        if (json!=null || !json.isEmpty()) {
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(json, Recipe.class);
            if (recipe == null){
                Log.i(TAG, "Recipe loaded from json is null!!");
                return null;
            }
            Log.i(TAG, "loadRecipeFromJson: loaded recipe "+recipe.getName());
            return recipe;
        }
        return null;
    }

    //update stored values of all recipes in SharedPreferences
    public void updatePreferenceRecipe(Recipe recipe, int recipeId, int appWidgetId, Context context){
        int previousId = mPreferences.getInt(mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY), INVALID_RECIPE_ID);

        SharedPreferences.Editor editor = mPreferences.edit();
        if(previousId != INVALID_RECIPE_ID){
            editor.remove(mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY)+appWidgetId);//clean previous recipe stored in Preferences
        }
        editor.putInt(mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY), recipeId);
        editor.putString(
                mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY)+appWidgetId,
                recipe.toString()
        );
        editor.apply();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_ingredients_list_widget);
        Log.i(TAG, "Stored recipe id="+recipeId+" in preferences, value: "+recipe.toString());
    }

    private String getRecipeStrFromPreferences(int appWidgetId){
        int recipeId = mPreferences.getInt(mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY), INVALID_RECIPE_ID);
        return mPreferences.getString(mContext.getString(R.string.PREFERENCE_RECIPE_ID_KEY)+appWidgetId, "");
    }

    public List<Recipe> validateStringsInRecipes(List<Recipe> recipeList){
        for (Recipe recipe : recipeList){
            for(Step step : recipe.getSteps()){
                if(step.getDescription().contains("\"")){
                    step.setDescription(step.getDescription().replace("\"", ""));
                }
            }
        }
        return recipeList;
    }
}
