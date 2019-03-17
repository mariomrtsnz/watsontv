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
        Date date = myFormat.parse(releaseDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
