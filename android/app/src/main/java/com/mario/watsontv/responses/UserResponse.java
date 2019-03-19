package com.mario.watsontv.responses;

import java.util.List;

public class UserResponse {
    private String id;
    private String email;
    private String password;
    private String name;
    private String role;
    private String picture;
    private List<String> watchlist;
    private List<String> watched;
    private List<String> likes;
    private List<String> friends;
    private boolean friended;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<String> watchlist) {
        this.watchlist = watchlist;
    }

    public List<String> getWatched() {
        return watched;
    }

    public void setWatched(List<String> watched) {
        this.watched = watched;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public boolean isFriended() {
        return friended;
    }

    public void setFriended(boolean friended) {
        this.friended = friended;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "_id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
