package com.android.project3.recipesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Ingredient;

import java.util.List;

/**
 * Created by katanbern on 21/02/2018.
 */

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsListViewHolder> {
    static final String TAG = IngredientsListAdapter.class.getSimpleName();
    List<Ingredient> mIngredientsList;

    public IngredientsListAdapter(){

    }

    @Override
    public IngredientsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(R.layout.ingredient_item_list, parent, shouldAttachToParent);
        return new IngredientsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsListViewHolder holder, int position) {
        if (mIngredientsList != null){
            Ingredient ingredient = mIngredientsList.get(position);
            String quantity = String.valueOf(ingredient.getQuantity());
            String unit = ingredient.getMeasure();
            String name = ingredient.getIngredient();

            holder.mQuantity.setText(quantity);
            holder.mUnit.setText(unit);
            holder.mIngredientName.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        if (mIngredientsList != null){
            return mIngredientsList.size();
        }
        return 0;
    }

    public void setIngredientsList(List<Ingredient> ingredients){
        mIngredientsList = ingredients;
        notifyDataSetChanged();
    }

    class IngredientsListViewHolder extends RecyclerView.ViewHolder{
        TextView mQuantity;
        TextView mUnit;
        TextView mIngredientName;

        public IngredientsListViewHolder(View itemView) {
            super(itemView);

            mQuantity = (TextView)itemView.findViewById(R.id.tv_quantity_ingr);
            mUnit = (TextView)itemView.findViewById(R.id.tv_unit_ingr);
            mIngredientName = (TextView)itemView.findViewById(R.id.tv_name_ingr);
        }
    }
}
