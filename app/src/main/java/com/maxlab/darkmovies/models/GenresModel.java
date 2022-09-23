package com.maxlab.darkmovies.models;

public class GenresModel {
    private String genreId, genreUid, genreName;

    public GenresModel(String genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public GenresModel() {
    }

    public String getGenreUid() {
        return genreUid;
    }

    public void setGenreUid(String genreUid) {
        this.genreUid = genreUid;
    }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

}
