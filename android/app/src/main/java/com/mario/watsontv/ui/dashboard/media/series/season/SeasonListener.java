package com.mario.watsontv.ui.dashboard.media.series.season;

public interface SeasonListener {

    void updateWatched(String id);

    void updateWatchlisted(String id);

    void updateCollected(String id);
}
