package com.ks.instagramclient;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by skammila on 2/7/16.
 */
public class TagClickableSpan extends ClickableSpan {

    int type;// 0-hashtag , 1- mention, 2- url link
    String text;// Keyword or url
    String time;

    public TagClickableSpan(String text, int type) {
        this.text = text;
        this.type = type;
        this.time = time;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //adding colors
//        if (type == 1)
//            ds.setColor(InstagramIndetail.this.getResources().getColor(
//                    R.color.link_color_mention));
//        else if (type == 2)
//            ds.setColor(InstagramIndetail.this.getResources().getColor(
//                    R.color.link_color_url));
//        else
        ds.setColor(Color.parseColor("#4aa7bb"));
        ds.setUnderlineText(false);
        // ds.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void onClick(View v) {

//        Debug.e("click done", "ok " + text);
        if (type == 0) {
            //pass hashtags to activity using intents
        } else if (type == 1) {
            //do for mentions
        } else {
            // passing weblinks urls to webview activity
//            startWebViewActivity(text);
        }
    }
}
