package com.android.project3.recipesapp.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by karina.bernice on 26/03/2018.
 */

public class WidgetListAdapter extends RecyclerView.Adapter {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class WidgetListViewHolder extends RecyclerView.ViewHolder {
        public WidgetListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
