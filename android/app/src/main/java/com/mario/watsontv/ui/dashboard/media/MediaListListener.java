package com.mario.watsontv.ui.dashboard.media;

public interface MediaListListener {
    void updateWatched(String id);
    void updateWatchlisted(String id);
    void updateCollected(String id);
    void goToDetail(String id, String mediaType);
}
