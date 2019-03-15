package com.mario.watsontv.responses;

import java.util.List;

public class SeasonResponse {
    int number;
    List<EpisodeResponse> episodes;

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
