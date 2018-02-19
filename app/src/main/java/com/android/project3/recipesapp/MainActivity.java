package com.android.project3.recipesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.project3.recipesapp.data.Recipe;
import com.android.project3.recipesapp.service.BaseService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseService.OnApiServiceListener{
    List<Recipe> mRecipesList;
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_first);
        loadRecipes();
    }

    public void loadRecipes(){
        BaseService service = new BaseService(this);
        service.executeService();
    }

    @Override
    public void onExecFinished(List<Recipe> recipes) {
        mRecipesList = recipes;
        mTextView.setText(recipes.get(0).getName());
    }
}
