package com.mario.watsontv.ui.dashboard.user.friends.detail;

public interface UserDetailsListener {
    void goToStats(String id);
    void goToCollections(String id);
    void updateFriend(String selectedUserId);
}
