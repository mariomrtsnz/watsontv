package com.mario.watsontv.ui.dashboard.media.series;

public interface MediaListListener {
    void updateWatched(String id);
    void updateWatchlisted(String id);
    void updateCollected(String id);
}
