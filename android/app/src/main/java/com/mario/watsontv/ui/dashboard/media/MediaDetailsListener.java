package com.mario.watsontv.ui.dashboard.media;

public interface MediaDetailsListener {
    void goToGenreMedia(String genreId);
    void updateWatched(String id);
    void updateWatchlisted(String id);
    void updateCollected(String id);
}
