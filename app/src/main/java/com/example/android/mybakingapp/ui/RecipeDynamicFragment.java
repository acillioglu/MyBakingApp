package com.example.android.mybakingapp.ui;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeDynamicFragment extends Fragment {

    private static final String SIS_PLAYER_POSITION = "sis_current_position";
    private static final String SIS_PLAY_WHEN_READY = "sis_playwhenready";
    private static final String BUNDLE_KEY_POSITION = "bundle_key_position";
    private static final String BUNDLE_KEY_SECOND_RECIPE = "bundle_key_second_recipe";
    public static long mCurrentPlayerPosition;
    public static boolean playWhenReady = true;
    private static int statePosition;
    @Nullable
    @BindView(R.id.simple_exoplayer_view)
    SimpleExoPlayerView mPlayerView;
    @Nullable
    @BindView(R.id.txt_description)
    TextView textView;
    @BindView(R.id.dynamic_linear)
    LinearLayout linearLayout;
    private List<RecipeList> recipeLists;
    private SimpleExoPlayer mExoPlayer;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailURL;
    private Uri mMediaUri;
    private boolean isLandscape = false;
    private int position = 0;
    private Unbinder unbinder;


    public RecipeDynamicFragment() {

    }

    @OnClick(R.id.btn_prev)
    public void prevButton() {

        int currentStep = recipeLists.get(0).getSteps().get(position).getId();


        if (currentStep > 0) {
            if (mExoPlayer != null) {

                mCurrentPlayerPosition = 0;
                playWhenReady = true;
                mExoPlayer.stop();


            }
            MyButtonClick myButtonClick = (MyButtonClick) getActivity();
            myButtonClick.onItemClick(position - 1);

        } else {

            Snackbar snackbar = Snackbar
                    .make(linearLayout, getResources().getString(R.string.info_firststep), Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }


    @OnClick(R.id.btn_next)
    public void nextButton() {

        int currentStep = recipeLists.get(0).getSteps().get(position).getId();
        int lastStep = recipeLists.get(0).getSteps().size();


        if (currentStep < lastStep - 1) {
            if (mExoPlayer != null) {

                mCurrentPlayerPosition = 0;
                playWhenReady = true;
                mExoPlayer.stop();

            }

            MyButtonClick myItemClick = (MyButtonClick) getActivity();
            myItemClick.onItemClick(position + 1);

        } else {

            Snackbar snackbar = Snackbar
                    .make(linearLayout, getResources().getString(R.string.info_laststep), Snackbar.LENGTH_LONG);

            snackbar.show();


        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_dynamic, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        checkLandscape();


        recipeLists = getArguments().getParcelableArrayList(BUNDLE_KEY_SECOND_RECIPE);
        position = getArguments().getInt(BUNDLE_KEY_POSITION);


        shortDescription = recipeLists.get(0).getSteps().get(position).getShortDescription();
        description = recipeLists.get(0).getSteps().get(position).getDescription();
        videoUrl = recipeLists.get(0).getSteps().get(position).getVideoURL();
        thumbnailURL = recipeLists.get(0).getSteps().get(position).getThumbnailURL();


        if (description != null && !description.isEmpty()) {
            textView.setText(description);
        }

        return rootView;
    }

    private void initializePlayer() {

        if (mExoPlayer == null) {

            if (videoUrl != null && !videoUrl.isEmpty()) {
                mMediaUri = Uri.parse(videoUrl);

                try {
                    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                    TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                    mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                    String userAgent = Util.getUserAgent(getContext(), "MyBakingApp");
                    MediaSource mediaSource = new ExtractorMediaSource(mMediaUri, new DefaultDataSourceFactory(
                            getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                    mPlayerView.setPlayer(mExoPlayer);
                    mExoPlayer.prepare(mediaSource);


                    if (mCurrentPlayerPosition != C.POSITION_UNSET && mCurrentPlayerPosition != 0) {
                        mExoPlayer.seekTo(mCurrentPlayerPosition);
                    } else {
                        mExoPlayer.seekTo(0);
                    }


                    mExoPlayer.setPlayWhenReady(playWhenReady);


                } catch (Exception e) {

                }

                if (isLandscape) {

                    expandVideoView(mPlayerView);
                    hideSystemUI();

                }


            } else {
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.img_novide));
            }
        }


    }

    private void checkLandscape() {

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
        } else {
            isLandscape = false;
        }


    }

    public void releasePlayer() {
        if (mExoPlayer != null && mPlayerView != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void expandVideoView(SimpleExoPlayerView exoPlayer) {
        exoPlayer.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        exoPlayer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {

            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentPlayerPosition = mExoPlayer.getCurrentPosition();
            statePosition = position;
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (Util.SDK_INT > 23) {
            if (mExoPlayer != null) {
                playWhenReady = mExoPlayer.getPlayWhenReady();
                releasePlayer();
            }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

        unbinder.unbind();

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(SIS_PLAYER_POSITION, mCurrentPlayerPosition);
        outState.putBoolean(SIS_PLAY_WHEN_READY, playWhenReady);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);


        if (savedInstanceState != null) {
            mCurrentPlayerPosition = savedInstanceState.getLong(SIS_PLAYER_POSITION);
            playWhenReady = savedInstanceState.getBoolean(SIS_PLAY_WHEN_READY);
        }


        if (statePosition != position) {
            mCurrentPlayerPosition = 0;
            playWhenReady = true;
        }


    }


    interface MyButtonClick {
        void onItemClick(int position);
    }

}

