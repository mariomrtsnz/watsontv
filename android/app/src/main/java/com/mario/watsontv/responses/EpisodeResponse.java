package com.mario.watsontv.responses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class EpisodeResponse {
    String id;
    String name;
    String synopsis;
    int duration;
    int number;
    String airTime;
    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Calendar getAirTime() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myFormat.parse(airTime));
        return cal;
    }

    public void setAirTime(String airTime) {
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
