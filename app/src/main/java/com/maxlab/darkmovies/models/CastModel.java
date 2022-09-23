package com.maxlab.darkmovies.models;

public class CastModel {

    private String castId, castName, castImage, castMname, castUid, movieId ;

    public CastModel(String castId, String castName, String castImage, String castMname, String castUid) {
        this.castName = castName;
        this.castImage = castImage;
        this.castMname = castMname;
        this.castUid = castUid;
        this.castId = castId;
    }

    public CastModel() {
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCastId() {
        return castId;
    }

    public void setCastId(String castId) {
        this.castId = castId;
    }

    public String getCastUid() {
        return castUid;
    }

    public void setCastUid(String castUid) {
        this.castUid = castUid;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCastImage() {
        return castImage;
    }

    public void setCastImage(String castImage) {
        this.castImage = castImage;
    }

    public String getCastMname() {
        return castMname;
    }

    public void setCastMname(String castMname) {
        this.castMname = castMname;
    }
}
