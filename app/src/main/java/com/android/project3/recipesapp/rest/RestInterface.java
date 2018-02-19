package com.android.project3.recipesapp.rest;

import com.android.project3.recipesapp.data.Recipe;
import com.squareup.okhttp.Callback;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by katanbern on 18/02/2018.
 */

public interface RestInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    public Call<List<Recipe>> getRecipes();
}
