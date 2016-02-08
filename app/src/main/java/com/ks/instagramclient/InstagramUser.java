package com.ks.instagramclient;

/**
 * Created by skammila on 2/3/16.
 */
public class InstagramUser {
    private String name;
    private String profilePicture;
    private String id;
    private String fullName;

    public InstagramUser() {
    }

    public InstagramUser(String name, String profilePicture, String id, String fullName) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.id = id;
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
