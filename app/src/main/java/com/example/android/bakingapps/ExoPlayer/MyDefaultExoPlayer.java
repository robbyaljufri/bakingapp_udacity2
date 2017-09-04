
package com.example.android.bakingapps.ExoPlayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;





public abstract class MyDefaultExoPlayer implements MyExoPlayer {

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;
    private Context context;
    private DataSource.Factory dataSourceFactory;

    private long playBackPosition;
    private int currentWindow;
    private boolean playWhenReady;

    public MyDefaultExoPlayer(SimpleExoPlayerView exoPlayerView, Context context, DataSource.Factory dataSourceFactory) {
        this.exoPlayerView = exoPlayerView;
        this.context = context;
        this.dataSourceFactory = dataSourceFactory;
    }

    public SimpleExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public void InitializePlayer(String URL)
    {
        exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(context), new DefaultTrackSelector(), new DefaultLoadControl());
        exoPlayer.prepare(buildMediaSource(URL), true, false);
        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playBackPosition);

        exoPlayerView.setPlayer(exoPlayer);
    }

    public void SetPlayerStateAndPosition(long playBackPosition, int currentWindow, boolean playWhenReady)
    {
        this.playBackPosition = playBackPosition;
        this.currentWindow = currentWindow;
        this.playBackPosition = playBackPosition;
    }

    public MediaSource buildMediaSource(String URL)
    {
        Uri uri = Uri.parse(URL);

        return new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(), null, null);
    }

    @SuppressLint("InlinedApi")
    public void hideSystemUI()
    {
        exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void ReleasePlayer()
    {
        if(exoPlayer != null)
        {
            playBackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();

            exoPlayer.release();
            exoPlayer = null;
        }
    }

    public long getPlayBackPosition() {
        return playBackPosition;
    }

    public int getCurrentWindow() {
        return currentWindow;
    }

    public boolean isPlayWhenReady() {
        return playWhenReady;
    }
}

