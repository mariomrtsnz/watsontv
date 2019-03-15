package com.mario.watsontv.responses;

import java.time.LocalDateTime;

public class EpisodeResponse {
    String name;
    String synopsis;
    LocalDateTime airTime;
    int duration;
    int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDateTime getAirTime() {
        return airTime;
    }

    public void setAirTime(LocalDateTime airTime) {
        this.airTime = airTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
