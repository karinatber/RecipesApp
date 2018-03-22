package com.android.project3.recipesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karinatber.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Recipe;

import java.util.List;

/**
 * Created by katanbern on 19/02/2018.
 */

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder> {
    private static final String TAG = RecipesListAdapter.class.getSimpleName();
    private List<Recipe> mRecipesList;
    OnRecipeClickListener mOnRecipeClickListener;

    public RecipesListAdapter(OnRecipeClickListener onRecipeClickListener){
        mOnRecipeClickListener = onRecipeClickListener;
    }

    public interface OnRecipeClickListener{
        void onRecipeClick(Recipe recipe, int position);
    }

    @Override
    public RecipesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder was called");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(R.layout.recipe_item_list, parent, shouldAttachToParent);
        return new RecipesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesListViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder was called");
        Recipe recipeItem;
        if (mRecipesList != null){
            recipeItem = mRecipesList.get(position);
            holder.mRecipeName.setText(recipeItem.getName());
            Log.i(TAG, "onBindViewHolder: recipe#"+position+" is "+recipeItem.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipesList != null) {
            return mRecipesList.size();
        }
        return 0;
    }

    class RecipesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mRecipeName;
        Context mContext;

        public RecipesListViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mRecipeName = (TextView)itemView.findViewById(R.id.tv_recipe_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipesList.get(adapterPosition);
            mOnRecipeClickListener.onRecipeClick(recipe, adapterPosition);
        }
    }

    public void setRecipesList(List<Recipe> recipes){
        mRecipesList = recipes;
        notifyDataSetChanged();
    }
}
