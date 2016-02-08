package com.ks.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by skammila on 2/3/16.
 */
public class InstagramAdapter extends ArrayAdapter {

    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView home;
    }


    public InstagramAdapter(Context context, List objects) {
        super(context, R.layout.item_instagram, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemInstagramPost post = (ItemInstagramPost)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_instagram, parent, false);
        }
        // Lookup view for data population
        // Populate the data into the template view using the data object
        ImageView postImage = (ImageView) convertView.findViewById(R.id.image_post);
        ImageView playImageBtn = (ImageView) convertView.findViewById(R.id.VideoPreviewPlayButton);
        if (post.getType().equals("image") && post.getImageLink() != null && post.getImageLink().length() > 0) {
//            playImageBtn.setVisibility(View.INVISIBLE);
            Picasso.with(getContext()).load(post.getImageLink()).placeholder(R.drawable.progress_large).into(postImage);
        } else if (post.getType().equals("video") && post.getVideoLink() != null && post.getVideoLink().length() > 0) {
            playImageBtn.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(post.getImageLink()).placeholder(R.drawable.progress_large).into(postImage);
            String videoLink = post.getVideoLink();
            postImage.setOnClickListener(new ImageClickListener(videoLink));
        } else {
            postImage.setVisibility(View.INVISIBLE);
        }

        Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
        String hashURL = "https://www.instagram.com";


        //Caption
        TextView caption = (TextView) convertView.findViewById(R.id.tvCaption);
        String cpt = post.getCaption() != null ? post.getCaption().getText() : "";
//        caption.setText(cpt);
        Linkfiy(cpt, caption);

        //ProfilePicture
        ImageView profile = (ImageView) convertView.findViewById(R.id.ivProfile);
        String profilePic = post.getFromUser() != null ? post.getFromUser().getProfilePicture() : "";
        Picasso.with(getContext()).load(profilePic).transform(new CircleTransformation()).into(profile);

        //UserName
        TextView un = (TextView) convertView.findViewById(R.id.tvUn);
        String username = post.getFromUser() != null ? post.getFromUser().getName() : "";
        un.setText(username);

        //likes
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        int clr = Color.parseColor("#4aa7bb");
        ForegroundColorSpan redForegroundColorSpan = new ForegroundColorSpan(clr);
        SpannableStringBuilder ssb = new SpannableStringBuilder(post.getLikes_count().toString());
        ssb.setSpan(redForegroundColorSpan,  0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" likes");
        tvLikes.setText(ssb, TextView.BufferType.NORMAL);


        //comments
        TextView comments = (TextView) convertView.findViewById(R.id.tvComments);
        String commentText = "<i>View all " + (post.getComments() != null ? post.getComments().size() : "0") + " comments</i>";
        comments.setText(Html.fromHtml(commentText));

        //comment1
        if (post.getComments() != null && post.getComments().size() >= 1) {
            TextView cmt1un = (TextView) convertView.findViewById(R.id.tvComment1un);
            cmt1un.setText(post.getComments().get(0).getFrom().getName());
            TextView cmt1 = (TextView) convertView.findViewById(R.id.tvComment1);
            Linkfiy(post.getComments().get(0).getText(), cmt1);
        }

        //comment2
        if (post.getComments() != null && post.getComments().size() >= 2) {
            TextView cmt2un = (TextView) convertView.findViewById(R.id.tvComment2un);
            cmt2un.setText(post.getComments().get(1).getFrom().getName());
            TextView cmt2 = (TextView) convertView.findViewById(R.id.tvComment2);
            Linkfiy(post.getComments().get(1).getText(), cmt2);
        }



        //relative tim stamp
//        CharSequence relTime = DateUtils.getRelativeTimeSpanString(getContext(), Long.parseLong(post.getCreated_time()) * 1000 );
        String relTime = getRelativeTimeSpan(Long.parseLong(post.getCreated_time()) * 1000);


        TextView time = (TextView) convertView.findViewById(R.id.tvRelTime);
        time.setText(relTime);


        // Return the completed view to render on screen
        return convertView;


    }

    public ArrayList<int[]> getSpans(String body, char prefix) {
        ArrayList<int[]> spans = new ArrayList<int[]>();

        Pattern pattern = Pattern.compile(prefix + "\\w+");
        Matcher matcher = pattern.matcher(body);

        // Check all occurrences
        while (matcher.find()) {
            int[] currentSpan = new int[2];
            currentSpan[0] = matcher.start();
            currentSpan[1] = matcher.end();
            spans.add(currentSpan);
        }

        return  spans;
    }

    public String getRelativeTimeSpan(long timeMilli) {
        long nowLngTime = System.currentTimeMillis();
        DateTime dateTime = new DateTime(timeMilli);
        DateTime now = new DateTime();
        long difference = Math.abs(timeMilli - nowLngTime);
        Period period = new Period(dateTime, now);
        PeriodFormatterBuilder formatterBuilder = new PeriodFormatterBuilder();
        if (difference > DateUtils.YEAR_IN_MILLIS) {
            formatterBuilder.appendYears().appendSuffix("y");
        } else if (difference > DateUtils.DAY_IN_MILLIS * 30) {
            formatterBuilder.appendMonths().appendSuffix("mo");
        } else if (difference > DateUtils.WEEK_IN_MILLIS) {
            formatterBuilder.appendWeeks().appendSuffix("w");
        } else if (difference > DateUtils.DAY_IN_MILLIS) {
            formatterBuilder.appendDays().appendSuffix("d");
        } else if (difference > DateUtils.HOUR_IN_MILLIS) {
            formatterBuilder.appendHours().appendSuffix("h");
        } else if (difference > DateUtils.MINUTE_IN_MILLIS) {
            formatterBuilder.appendMinutes().appendSuffix("mi");
        } else if (difference > DateUtils.SECOND_IN_MILLIS) {
            formatterBuilder.appendSeconds().appendSuffix("s");
        }
        String ends = formatterBuilder.printZeroNever().toFormatter().print(period);
//        String plural = ends.startsWith("1 ")?"":"s";
        return ends;
    }

    private void Linkfiy(String a, TextView textView) {

        Pattern urlPattern = Patterns.WEB_URL;
        Pattern mentionPattern = Pattern.compile("(@[A-Za-z0-9_-]+)");
        Pattern hashtagPattern = Pattern.compile("#(\\w+|\\W+)");

        Matcher o = hashtagPattern.matcher(a);
        Matcher mention = mentionPattern.matcher(a);
        Matcher weblink = urlPattern.matcher(a);


        SpannableString spannableString = new SpannableString(a);
        //#hashtags

        while (o.find()) {

            spannableString.setSpan(new TagClickableSpan(o.group(),
                            0), o.start(), o.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // --- @mention
        while (mention.find()) {
            spannableString.setSpan(
                    new TagClickableSpan(mention.group(), 1), mention.start(), mention.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //@weblink
        while (weblink.find()) {
            spannableString.setSpan(
                    new TagClickableSpan(weblink.group(), 2), weblink.start(), weblink.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
