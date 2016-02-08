package com.ks.instagramclient;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by skammila on 2/7/16.
 */
public class PlayVideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player_view);
        final VideoView vd = (VideoView) findViewById(R.id.video);
        String url = null;
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("video_url");
            System.out.println(url);
            getIntent().removeExtra("video_url");

            if (url != null) {
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(vd);
                vd.setMediaController(mediaController);
                vd.setOnCompletionListener(this);
                vd.setVideoPath(url);
                vd.requestFocus();
                vd.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {
                        vd.start();
                    }
                });
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        this.finish();
    }
}
