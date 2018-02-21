package com.android.project3.recipesapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.service.BaseService;

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

    @Override
    public void onRecipeSelected(int position, List<Recipe> recipes) {
        mMainRecipeList = recipes;
        Toast.makeText(this, "Recipe selected: "+recipes.get(position).getName(), Toast.LENGTH_SHORT).show();
    }

    public void handleTransition(int event){

    }
}
