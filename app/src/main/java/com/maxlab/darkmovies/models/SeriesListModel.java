package com.maxlab.darkmovies.models;

import java.util.ArrayList;
import java.util.List;

public class SeriesListModel {

    String seriesListName;
    List<SeriesModel> seriesModels = new ArrayList<>();

    public SeriesListModel(String seriesListName, List<SeriesModel> seriesModels) {
        this.seriesListName = seriesListName;
        this.seriesModels = seriesModels;
    }

    public String getSeriesListName() {
        return seriesListName;
    }

    public void setSeriesListName(String seriesListName) {
        this.seriesListName = seriesListName;
    }

    public List<SeriesModel> getSeriesModels() {
        return seriesModels;
    }

    public void setSeriesModels(List<SeriesModel> seriesModels) {
        this.seriesModels = seriesModels;
    }
}
