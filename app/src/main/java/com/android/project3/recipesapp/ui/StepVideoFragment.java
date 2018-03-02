package com.android.project3.recipesapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Step;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;


/**
 * Created by katanbern on 28/02/2018.
 */

public class StepVideoFragment extends Fragment {
    TextView mVideoUrlTextView;
    SimpleExoPlayerView mExoPlayerView;
    Step mStep;

    public StepVideoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_video_fragment, container, false);

        mExoPlayerView = (SimpleExoPlayerView)rootView.findViewById(R.id.sep_test_video_url);

        if(mStep != null){
            mVideoUrlTextView.setText(mStep.getVideoURL()+"\n"+mStep.getDescription());
            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setStepData(Step step){
        mStep = step;
    }
}
