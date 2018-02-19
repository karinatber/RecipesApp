package com.android.project3.recipesapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.service.BaseService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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
}
