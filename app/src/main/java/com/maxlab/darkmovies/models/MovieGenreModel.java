package com.maxlab.darkmovies.models;

public class MovieGenreModel {
    private String genreName;

    public MovieGenreModel(String genreName) {
        this.genreName = genreName;
    }

    public MovieGenreModel() {
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
