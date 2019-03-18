package com.mario.watsontv.responses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MediaResponse {
    String id;
    String title;
    int[] rating;
    String[] cast;
    String coverImage;
    GenreResponse genre;
    String synopsis;
    String mediaType;
    String releaseDate;
    String broadcaster;
    String[] seasons;
    int airsDayOfWeek;
    String trailer;
    boolean isWatched;
    boolean isWatchlisted;
    boolean isCollected;
    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getRating() {
        return rating;
    }

    public void setRating(int[] rating) {
        this.rating = rating;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public GenreResponse getGenre() {
        return genre;
    }

    public void setGenre(GenreResponse genre) {
        this.genre = genre;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(String broadcaster) {
        this.broadcaster = broadcaster;
    }

    public String[] getSeasons() {
        return seasons;
    }

    public void setSeasons(String[] seasons) {
        this.seasons = seasons;
    }

    public int getAirsDayOfWeek() {
        return airsDayOfWeek;
    }

    public void setAirsDayOfWeek(int airsDayOfWeek) {
        this.airsDayOfWeek = airsDayOfWeek;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Calendar getReleaseDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myFormat.parse(releaseDate));
        return cal;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isWatched() {
        return isWatched;
    }

    public void setWatched(boolean watched) {
        isWatched = watched;
    }

    public boolean isWatchlisted() {
        return isWatchlisted;
    }

    public void setWatchlisted(boolean watchlisted) {
        isWatchlisted = watchlisted;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

}
