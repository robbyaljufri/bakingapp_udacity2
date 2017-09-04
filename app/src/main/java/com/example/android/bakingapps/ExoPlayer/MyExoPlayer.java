
package com.example.android.bakingapps.ExoPlayer;

import android.annotation.SuppressLint;
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





public interface MyExoPlayer {

    SimpleExoPlayer getExoPlayer();
    void SetPlayerStateAndPosition(long playBackPosition, int currentWindow, boolean playWhenReady);
    void InitializePlayer(String URL);

    @SuppressLint("InlinedApi")
    void hideSystemUI();

    void ReleasePlayer();
    long getPlayBackPosition();
    int getCurrentWindow();
    boolean isPlayWhenReady();
}

