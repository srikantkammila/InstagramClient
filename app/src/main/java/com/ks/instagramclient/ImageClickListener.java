package com.ks.instagramclient;

import android.content.Intent;
import android.view.View;

/**
 * Created by skammila on 2/7/16.
 */
public class ImageClickListener implements View.OnClickListener {

    String videoLink;

    public ImageClickListener(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(v.getContext(), PlayVideoActivity.class);
        i.putExtra("video_url", videoLink);
        v.getContext().startActivity(i);
    }
}
