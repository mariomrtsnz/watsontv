package com.mario.watsontv.responses;

import java.util.List;

public class DashboardResponse {
    List<MediaResponse> upNext;
    List<MediaResponse> upcoming;

    public List<MediaResponse> getUpNext() {
        return upNext;
    }

    public void setUpNext(List<MediaResponse> upNext) {
        this.upNext = upNext;
    }

    public List<MediaResponse> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<MediaResponse> upcoming) {
        this.upcoming = upcoming;
    }
}
