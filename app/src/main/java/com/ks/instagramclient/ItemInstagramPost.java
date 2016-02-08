package com.ks.instagramclient;

import java.util.List;

/**
 * Created by skammila on 2/3/16.
 */
public class ItemInstagramPost {
    private String imageLink;
    private String videoLink;
    private String created_time;
    private List<InstagramComment> comments;
    private long likes_count;
    private InstagramCaption caption;
    private InstagramUser user;
    private String type;

    public String getImageLink() {
        return imageLink;
    }

    public String getCreated_time() {
        return created_time;
    }

    public List<InstagramComment> getComments() {
        return comments;
    }

    public Long getLikes_count() {
        return likes_count;
    }

    public InstagramUser getFromUser() {
        return user;
    }

    public InstagramCaption getCaption() {
        return caption;
    }

    public void setImageLink(String link) {
        this.imageLink = link;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public void setComments(List<InstagramComment> comments) {
        this.comments = comments;
    }

    public void setLikes_count(long likes_count) {
        this.likes_count = likes_count;
    }

    public void setCaption(InstagramCaption caption) {
        this.caption = caption;
    }

    public void setFromUser(InstagramUser user) {
        this.user = user;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
