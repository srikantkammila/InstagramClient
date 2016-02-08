package com.ks.instagramclient;

/**
 * Created by skammila on 2/3/16.
 */
public class InstagramComment {
    private String text;
    private InstagramUser from;
    private String id;
    private String create_time;

    public InstagramComment() {
    }

    public InstagramComment(String text, InstagramUser from, String id, String create_time) {
        this.text = text;
        this.from = from;
        this.id = id;
        this.create_time = create_time;
    }

    public String getText() {
        return text;
    }

    public InstagramUser getFrom() {
        return from;
    }

    public String getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrom(InstagramUser from) {
        this.from = from;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
