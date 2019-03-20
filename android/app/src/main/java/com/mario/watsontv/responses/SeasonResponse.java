package com.mario.watsontv.responses;

import java.util.List;

public class SeasonResponse {
    String id;
    int number;
    List<EpisodeResponse> episodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
