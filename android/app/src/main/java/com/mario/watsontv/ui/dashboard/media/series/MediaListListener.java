package com.mario.watsontv.ui.dashboard.media.series;

public interface MediaListListener {
    void updateWatched(String id, boolean watched);
    void updateWatchlisted(String id, boolean watchlisted);
    void updateCollected(String id, boolean collected);
}
