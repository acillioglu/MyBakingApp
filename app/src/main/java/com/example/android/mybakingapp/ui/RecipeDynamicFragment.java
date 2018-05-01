package com.example.android.mybakingapp.ui;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.mybakingapp.Model.RecipeList;
import com.example.android.mybakingapp.Model.Step;
import com.example.android.mybakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecipeDynamicFragment extends Fragment {

    private static final String SIS_PLAYER_POSITION = "sis_current_position";
    private static final String BUNDLE_KEY_POSITION = "bundle_key_position";
    private static final String BUNDLE_KEY_SECOND_RECIPE = "bundle_key_second_recipe";
    private List<RecipeList> recipeLists;
    private SimpleExoPlayer mExoPlayer;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailURL;
    private Uri mMediaUri;
    private boolean isLandscape = false;

    private int position = 0;

    private static long mCurrentPlayerPosition;

    private Unbinder unbinder;

    private int denemePosition;



    @Nullable
    @BindView(R.id.simple_exoplayer_view)
    SimpleExoPlayerView mPlayerView;
    @Nullable
    @BindView(R.id.txt_description)
    TextView textView;

    @OnClick(R.id.btn_prev)
    public void prevButton() {

        if (recipeLists.get(0).getSteps().get(position).getId() > 0) {
            if (mExoPlayer != null) {

                mExoPlayer.stop();
            }
            MyButtonClick myButtonClick = (MyButtonClick) getActivity();
            myButtonClick.onItemClick(position - 1);
        }
    }


    @OnClick(R.id.btn_next)
    public void nextButton() {

        if (recipeLists.get(0).getSteps().get(position).getId() < recipeLists.get(0).getSteps().size() - 1) {
            if (mExoPlayer != null) {

                mExoPlayer.stop();
            }

            MyButtonClick myItemClick = (MyButtonClick) getActivity();
            myItemClick.onItemClick(position + 1);


        }
    }


    public RecipeDynamicFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_dynamic, container, false);

       // ButterKnife.bind(this, rootView);

        unbinder = ButterKnife.bind(this,rootView);


        if (savedInstanceState != null) {

            mCurrentPlayerPosition = savedInstanceState.getLong(SIS_PLAYER_POSITION);
            denemePosition = savedInstanceState.getInt("sallama");


        }




        checkLandscape();


        recipeLists = getArguments().getParcelableArrayList(BUNDLE_KEY_SECOND_RECIPE);
        position = getArguments().getInt(BUNDLE_KEY_POSITION);


        if (denemePosition != position) {
          //  denemePosition = position;
            mCurrentPlayerPosition = 0;
        }






        shortDescription = recipeLists.get(0).getSteps().get(position).getShortDescription();
        description = recipeLists.get(0).getSteps().get(position).getDescription();
        videoUrl = recipeLists.get(0).getSteps().get(position).getVideoURL();
        thumbnailURL = recipeLists.get(0).getSteps().get(position).getThumbnailURL();


        if (description != null && !description.isEmpty()) {
            textView.setText(description);
        }

        if (videoUrl != null && !videoUrl.isEmpty()) {
            mMediaUri = Uri.parse(videoUrl);
            initializePlayer(mMediaUri);
        } else {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.img_novide));

        }


        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                String userAgent = Util.getUserAgent(getContext(), "MyBakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mPlayerView.setPlayer(mExoPlayer);
                mExoPlayer.prepare(mediaSource);


                if (mCurrentPlayerPosition != C.POSITION_UNSET) {
                    mExoPlayer.seekTo(mCurrentPlayerPosition);
                }

                mExoPlayer.setPlayWhenReady(true);


                // mPlayerView.hideController();

            } catch (Exception e) {
                Log.e("aslankral", "exoplayer error" + e.toString());
            }
        }

        if (isLandscape) {

            expandVideoView(mPlayerView);
            hideSystemUI();

            // mPlayerView.setResizeMode();

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
        if (mExoPlayer != null) {
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
      //  initializePlayer(mMediaUri);
    }

    @Override
    public void onStop() {
        super.onStop();


        releasePlayer();

        /*
        if (mExoPlayer != null) {
            playerStopPosition = mExoPlayer.getCurrentPosition();
            playerStopped = true;
            releasePlayer();

        }

        */
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        releasePlayer();
        unbinder.unbind();
    }


    @Override
    public void onPause() {
        super.onPause();



        if (mExoPlayer != null) {
            mCurrentPlayerPosition = mExoPlayer.getCurrentPosition();

        }





        /*
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
*/

    }



    @Override
    public void onResume() {
        super.onResume();




        /*

        if (videoUrl != null && !videoUrl.isEmpty()) {

            if (mExoPlayer != null) {
                mExoPlayer.seekTo(playerCurrentPosition);
            } else {
                initializePlayer(mMediaUri);
            }

        }
*/
    }


    interface MyButtonClick {
        public void onItemClick(int position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.v("OnSaveInstance", "" + mCurrentPlayerPosition);
        Log.v("OnSaveInstance", "" + position);

        outState.putLong(SIS_PLAYER_POSITION, mCurrentPlayerPosition);
        outState.putInt("sallama", position);
    }


}
