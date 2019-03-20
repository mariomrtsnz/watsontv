package com.mario.watsontv.responses;

import java.util.List;

public class SeasonDetailsResponse {
    String id;
    MediaResponse series;
    int number;
    List<EpisodeResponse> episodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MediaResponse getSeries() {
        return series;
    }

    public void setSeries(MediaResponse series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<EpisodeResponse> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeResponse> episodes) {
        this.episodes = episodes;
    }
}
