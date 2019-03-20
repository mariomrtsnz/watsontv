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
    int runtime;
    boolean watched;
    boolean watchlisted;
    boolean collected;
    String __t;
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

    public float getTotalRating() {
        float totalRating = 0;
        if (rating.length > 0) {
            for(int i = 0; i <= rating.length; i++) {
                totalRating += rating[i];
            }
            totalRating = totalRating/rating.length;
        }
        return totalRating;
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
        Calendar cal = null;

        if (releaseDate != null) {
            cal = Calendar.getInstance();
            cal.setTime(myFormat.parse(releaseDate));
        }
        return cal;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isWatchlisted() {
        return watchlisted;
    }

    public void setWatchlisted(boolean watchlisted) {
        this.watchlisted = watchlisted;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String get__t() {
        return __t;
    }

    public void set__t(String __t) {
        this.__t = __t;
    }
}
