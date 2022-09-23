package com.maxlab.darkmovies.models;

public class EpisodeModel {

    String episodeId, episodeName, episodeBanner, episodeUrl;

    public EpisodeModel(String episodeId, String episodeName, String episodeBanner) {
        this.episodeId = episodeId;
        this.episodeName = episodeName;
        this.episodeBanner = episodeBanner;
    }

    public EpisodeModel() {
    }

    public String getEpisodeUrl() {
        return episodeUrl;
    }

    public void setEpisodeUrl(String episodeUrl) {
        this.episodeUrl = episodeUrl;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getEpisodeBanner() {
        return episodeBanner;
    }

    public void setEpisodeBanner(String episodeBanner) {
        this.episodeBanner = episodeBanner;
    }

}
