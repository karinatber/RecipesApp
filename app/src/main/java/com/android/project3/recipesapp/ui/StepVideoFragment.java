package com.android.project3.recipesapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project3.recipesapp.R;
import com.android.project3.recipesapp.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;


/**
 * Created by katanbern on 28/02/2018.
 */

public class StepVideoFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = StepVideoFragment.class.getSimpleName();
    public static final int FIRST_STEP_ID = 0;

    TextView mStepFullDescr;
    SimpleExoPlayerView mExoPlayerView;
    SimpleExoPlayer mExoPlayer;
    Button mBtnPrevious;
    Button mBtnNext;
    Step mStep;
    List<Step> mStepList;
    int mStepId;
    SwitchRecipeListener mSwitchRecipeListener;

    public StepVideoFragment(){

    }

    public interface SwitchRecipeListener{
        void onRecipeChange(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mSwitchRecipeListener = (SwitchRecipeListener)context;
        } catch (Exception e){
            Log.e(TAG, "Must implement SwitchRecipeListener.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_video_fragment, container, false);

        mStepFullDescr = (TextView)rootView.findViewById(R.id.tv_step_full_description);
        mExoPlayerView = (SimpleExoPlayerView)rootView.findViewById(R.id.player_view);
        mBtnPrevious = (Button)rootView.findViewById(R.id.btn_previous_step);
        mBtnNext = (Button)rootView.findViewById(R.id.btn_next_step);

        mBtnPrevious.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);

        if(mStep != null){
            if(mStepId == FIRST_STEP_ID){
                mBtnPrevious.setClickable(false);
                mBtnNext.setClickable(true);
            } else if (mStepId == mStepList.size()-1){
                mBtnPrevious.setClickable(true);
                mBtnNext.setClickable(false);
            }
            mStepFullDescr.setText(mStep.getDescription());
            initializePlayer(mStep.getVideoURL());
            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setStepListAndPosition(List<Step> stepList, int stepId){
        mStepList = stepList;
        mStepId = stepId;
        mStep = mStepList.get(mStepId);
    }

    private void initializePlayer(String mediaStringUri){
        Uri mediaUri = prepareStepUri(mediaStringUri);
        if (mExoPlayer == null){
            /**create an instance of the ExoPlayer**/
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);
            /**prepare the media source**/
            String userAgent = Util.getUserAgent(getContext(), "RecipesApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private Uri prepareStepUri(String stepStringUri){
        return Uri.parse(stepStringUri).buildUpon().build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(mStepId == FIRST_STEP_ID){
            mBtnPrevious.setClickable(false);
            mBtnNext.setClickable(true);
        } else if (mStepId == mStepList.size()-1){
            mBtnPrevious.setClickable(true);
            mBtnNext.setClickable(false);
        }
        releasePlayer();
        switch (id){
            case R.id.btn_previous_step:
                Toast.makeText(getContext(), "Previous Step", Toast.LENGTH_SHORT).show();
                mSwitchRecipeListener.onRecipeChange(mStepId-1);
                break;
            case R.id.btn_next_step:
                Toast.makeText(getContext(), "Next Step", Toast.LENGTH_SHORT).show();
                mSwitchRecipeListener.onRecipeChange(mStepId+1);
                break;
        }
    }
}
