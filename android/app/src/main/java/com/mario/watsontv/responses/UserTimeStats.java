package com.mario.watsontv.responses;

public class UserTimeStats {
    private TimeStatsResponse episodes;
    private TimeStatsResponse movies;

    public TimeStatsResponse getEpisodes() {
        return episodes;
    }

    public void setEpisodes(TimeStatsResponse episodes) {
        this.episodes = episodes;
    }

    public TimeStatsResponse getMovies() {
        return movies;
    }

    public void setMovies(TimeStatsResponse movies) {
        this.movies = movies;
    }
}
