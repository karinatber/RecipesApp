package com.android.project3.recipesapp.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.rest.RestInterface;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by katanbern on 18/02/2018.
 */

public class BaseService {
    static final String TAG = BaseService.class.getSimpleName();
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    public static final int ACTION_LOAD_ALL_RECIPES = 0;
    public static final int ACTION_RECIPE_BY_ID = 1;


    private List<Recipe> mRecipesList;
    OnApiServiceListener listener;

    public BaseService(OnApiServiceListener listener){
        this.listener = listener;
    }

    public interface OnApiServiceListener{
        void onExecFinished(List<Recipe> recipes);
    }

    private static Retrofit getBuilder(){
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(3000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(3000, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(3000, TimeUnit.MILLISECONDS);


        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void executeService(Context context, int action){
        Log.i(TAG, "executeService was called");

        switch(action){
            case ACTION_LOAD_ALL_RECIPES:
                loadAllRecipes();
                break;
            case ACTION_RECIPE_BY_ID:
                loadRecipeByID(context);
                break;
        }
    }

    private void loadAllRecipes(){
        Retrofit retrofit = getBuilder();

        RestInterface service = retrofit.create(RestInterface.class);

        Call<List<Recipe>> call = service.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                if(recipes != null) {
                    mRecipesList = recipes;
                    listener.onExecFinished(recipes);
                    Log.i(TAG, "Loaded recipes with retrofit! First one: " + recipes.get(0).getName());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "Something went wrong on callback");
            }
        });
    }

    private void loadRecipeByID(Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFERENCE_RECIPE_KEY), Context.MODE_PRIVATE);
        int recipeID = sp.getInt(context.getString(R.string.PREFERENCE_RECIPE_KEY), 99);
        Recipe selectedRecipe;
        for (Recipe recipe : mRecipesList){
            if (recipe.getId() == recipeID){
                selectedRecipe = recipe;
                return;
            }
        }
        Log.v(TAG, "Could not load recipe by ID "+recipeID);
    }
}
