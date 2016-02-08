package com.ks.instagramclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skammila on 2/3/16.
 */
public class InstagramResponseParser {

    public static InstagramCaption getCaption(JSONObject cpt) throws JSONException{
        String cptText = cpt.optString("text") != null ? cpt.getString("text") : "";
        String cptId = cpt.optString("id") != null ? cpt.getString("id") : "";
        String cptTime = cpt.optString("created_time") != null ? cpt.getString("created_time") : "";
        JSONObject cptFrom = cpt.optJSONObject("from") != null ? cpt.getJSONObject("from") : null;
        InstagramUser from = cptFrom != null ? getUser(cptFrom) : null;

        InstagramCaption caption = new InstagramCaption(cptText, from, cptId, cptTime);


        return caption;

    }


    public static InstagramUser getUser(JSONObject usr) throws  JSONException{
        String usrname = usr.optString("username") != null ? usr.getString("username") : "";
        String profilePicture = usr.optString("profile_picture") != null ? usr.getString("profile_picture") : "";
        String usrId= usr.optString("id") != null ? usr.getString("id") : "";
        String usrFullName= usr.optString("full_name") != null ? usr.getString("full_name") : "";

        InstagramUser user = new InstagramUser(usrname, profilePicture, usrId, usrFullName);

        return user;

    }

    public static InstagramComment getComment(JSONObject cmt) throws JSONException{

        String cmtText = cmt.optString("text") != null ? cmt.getString("text") : "";
        String cmtId = cmt.optString("id") != null ? cmt.getString("id") : "";
        String cmtTime = cmt.optString("created_time") != null ? cmt.getString("created_time") : "";
        JSONObject cmtFrom = cmt.optJSONObject("from") != null ? cmt.getJSONObject("from") : null;
        InstagramUser from = cmtFrom != null ? getUser(cmtFrom) : null;

        InstagramComment comment = new InstagramComment(cmtText, from, cmtId, cmtTime);


        return comment;

    }


    public static ItemInstagramPost getPost(JSONObject post) throws JSONException{

        ItemInstagramPost instPost = new ItemInstagramPost();

        //read caption data
        if (post.optJSONObject("caption") != null) {
            JSONObject cpt = post.getJSONObject("caption");
            instPost.setCaption(getCaption(cpt));
        }

        //read comments
        if (post.optJSONObject("comments") != null && post.getJSONObject("comments").optJSONArray("data") != null && post.getJSONObject("comments").getJSONArray("data").length() > 0) {
            List<InstagramComment> comments = new ArrayList<InstagramComment>();
            JSONArray commentsJson = post.getJSONObject("comments").getJSONArray("data");
            for (int i=0; i < commentsJson.length(); i++) {
                comments.add(getComment(commentsJson.getJSONObject(i))); //create comments list
            }
            instPost.setComments(comments);
        }

        //read from user
        if (post.optJSONObject("user") != null) {
            JSONObject usr = post.getJSONObject("user");
            instPost.setFromUser(getUser(usr));
        }

        //read post data
        //type
        String type = post.optString("type") != null ? post.getString("type") : "";
        instPost.setType(type);
        //created time
        String createdTime = post.optString("created_time") != null ? post.getString("created_time") : "";
        instPost.setCreated_time(createdTime);

        //like count
        Long likesCount = post.optJSONObject("likes") != null && post.getJSONObject("likes").optString("count") != null ? post.getJSONObject("likes").getLong("count") : 0;
        instPost.setLikes_count(likesCount);

        String imageLink = post.optJSONObject("images") != null && post.getJSONObject("images").optJSONObject("standard_resolution") != null && post.getJSONObject("images").getJSONObject("standard_resolution").optString("url") != null ?
                post.getJSONObject("images").getJSONObject("standard_resolution").getString("url") : "";
        instPost.setImageLink(imageLink);

        String videoLink = post.optJSONObject("videos") != null && post.getJSONObject("videos").optJSONObject("standard_resolution") != null && post.getJSONObject("videos").getJSONObject("standard_resolution").optString("url") != null ?
                post.getJSONObject("videos").getJSONObject("standard_resolution").getString("url") : "";
        instPost.setVideoLink(videoLink);

        return instPost;

    }

    public static List<ItemInstagramPost> getInstPosts(JSONObject obj, InstagramAdapter adapter) throws  JSONException {
        List<ItemInstagramPost> posts = new ArrayList<ItemInstagramPost>();
        if (obj.optJSONArray("data") != null && obj.getJSONArray("data").length() > 0) {
            for (int i = 0; i < obj.getJSONArray("data").length(); i++) {
                JSONObject post = obj.getJSONArray("data").getJSONObject(i);
                ItemInstagramPost pst = getPost(post);
                adapter.add(pst);
                posts.add(pst);
            }
        }
        return posts;
    }
}
