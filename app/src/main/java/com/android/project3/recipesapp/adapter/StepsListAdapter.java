package com.android.project3.recipesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Step;

import java.util.List;

/**
 * Created by katanbern on 21/02/2018.
 */

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {
    static final String TAG = StepsListAdapter.class.getSimpleName();
    List<Step> mStepsList;
    OnStepClickListener mOnStepClickListener;

    public StepsListAdapter(OnStepClickListener onStepClickListener){
        mOnStepClickListener = onStepClickListener;
    }

    public interface OnStepClickListener{
        void onStepClick(int stepId);
    }

    @Override
    public StepsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.step_item_list, parent, false);
        return new StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsListViewHolder holder, int position) {
        if(mStepsList != null){
            Step step = mStepsList.get(position);
            String shortDescr = step.getShortDescription();

            holder.mShortDescr.setText(shortDescr);
        }
    }

    @Override
    public int getItemCount() {
        if(mStepsList != null){
            return mStepsList.size();
        }
        return 0;
    }

    public void setStepsList(List<Step> steps){
        mStepsList = steps;
        notifyDataSetChanged();
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mShortDescr;

        public StepsListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mShortDescr = (TextView)itemView.findViewById(R.id.tv_step_short_description);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Step step = mStepsList.get(position);
            mOnStepClickListener.onStepClick(position);
        }
    }
}
