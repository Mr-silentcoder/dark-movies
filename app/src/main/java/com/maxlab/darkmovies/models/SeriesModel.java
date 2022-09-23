package com.maxlab.darkmovies.models;

public class SeriesModel {

    private  String seriesId, seriesName , seriesYear, seriesImage , seriesRate, seriesUid,  seriesLng , seasons;

    public SeriesModel(String seriesId, String seriesName, String seriesImage, String seriesRate) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.seriesImage = seriesImage;
        this.seriesRate = seriesRate;
    }

    public SeriesModel() {
    }

    public String getSeriesYear() {
        return seriesYear;
    }

    public void setSeriesYear(String seriesYear) {
        this.seriesYear = seriesYear;
    }

    public String getSeasons() {
        return seasons;
    }

    public void setSeasons(String seasons) {
        this.seasons = seasons;
    }

    public String getSeriesLng() {
        return seriesLng;
    }

    public void setSeriesLng(String seriesLng) {
        this.seriesLng = seriesLng;
    }

    public String getSeriesUid() {
        return seriesUid;
    }

    public void setSeriesUid(String seriesUid) {
        this.seriesUid = seriesUid;
    }

    public String getSeriesImage() {
        return seriesImage;
    }

    public String getSeriesRate() {
        return seriesRate;
    }

    public void setSeriesRate(String seriesRate) {
        this.seriesRate = seriesRate;
    }

    public void setSeriesImage(String seriesImage) {
        this.seriesImage = seriesImage;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
