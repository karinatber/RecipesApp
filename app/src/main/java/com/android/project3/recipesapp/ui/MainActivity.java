package com.android.project3.recipesapp.ui;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.utils.RecipesAppUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.OnSelectRecipeInterface{
    List<Recipe> mMainRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();

        /**Inflate recipes list fragment**/
        RecipesListFragment recipesListFragment = new RecipesListFragment();
        manager.beginTransaction()
                .add(R.id.container_layout, recipesListFragment)
                .commit();
    }

    private int getAppWidgetId(){
        Intent intent = getIntent();
        int appWidgetid = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return appWidgetid;
    }

    @Override
    public void onRecipeSelected(int position, List<Recipe> recipes) {
        mMainRecipeList = recipes;
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(DetailsFragment.EXTRA_RECIPE, recipes.get(position));
        intent.putExtra(RecipesAppUtils.EXTRA_APPWIDGET_ID, getAppWidgetId());
        startActivity(intent);
    }
}
