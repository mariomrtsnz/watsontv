package com.mario.watsontv.ui.dashboard.media.series;

public interface MediaListListener {
    void addToCollection(String id);
    void removeFromCollection(String id);

    void updateWatched(String id, boolean watched);
    void updateWatchlisted(String id, boolean watchlisted);
}
