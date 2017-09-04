
package com.example.android.bakingapps.ExoPlayer;

import android.content.Context;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;




public class MyDefaultHttpExoExoPlayer extends MyDefaultExoPlayer {


    public MyDefaultHttpExoExoPlayer(SimpleExoPlayerView exoPlayerView, Context context)
    {
        super(exoPlayerView, context, new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "BakingApps")));
    }

}

