package com.maxlab.darkmovies.models;

public class SeasonsModel {
    String  seasonId, seasonName ,seriesLng;

    public SeasonsModel(String seasonId, String seasonName) {
        this.seasonId = seasonId;
        this.seasonName = seasonName;
    }

    public SeasonsModel() {
    }

    public String getSeriesLng() {
        return seriesLng;
    }

    public void setSeriesLng(String seriesLng) {
        this.seriesLng = seriesLng;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
