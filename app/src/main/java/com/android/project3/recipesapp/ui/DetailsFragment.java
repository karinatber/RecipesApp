package com.android.project3.recipesapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.project3.recipesapp.R;

/**
 * Created by katanbern on 21/02/2018.
 */

public class DetailsFragment extends Fragment {
    RecyclerView mRecyclerView;
    
    public DetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipes_list_fragment, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
