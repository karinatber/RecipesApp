package com.android.project3.recipesapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


/**
 * Created by katanbern on 28/02/2018.
 */

public class StepVideoFragment extends Fragment {
    TextView mStepFullDescr;
    SimpleExoPlayerView mExoPlayerView;
    SimpleExoPlayer mExoPlayer;
    Step mStep;

    public StepVideoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_video_fragment, container, false);

        mStepFullDescr = (TextView)rootView.findViewById(R.id.tv_step_full_description);
        mExoPlayerView = (SimpleExoPlayerView)rootView.findViewById(R.id.player_view);

        if(mStep != null){
            mStepFullDescr.setText(mStep.getDescription());
            initializePlayer(mStep.getVideoURL());
            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setStepData(Step step){
        mStep = step;
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
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
