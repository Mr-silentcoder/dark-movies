package com.maxlab.darkmovies.models;

public class MovieModel {
    public   String movieId, movieName, movieImage,movieRate,movieTime,movieYear,
                     movieLng, movieUid, movieBanner , movieUrl, trailerUrl;

    public MovieModel(String movieId, String movieName, String movieImage, String movieRate, String movieTime, String movieYear) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieImage = movieImage;
        this.movieRate = movieRate;
        this.movieTime = movieTime;
        this.movieYear = movieYear;

    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public MovieModel() {
    }

    public String getMovieBanner() {
        return movieBanner;
    }

    public void setMovieBanner(String movieBanner) {
        this.movieBanner = movieBanner;
    }

    public String getMovieLng() {
        return movieLng;
    }

    public void setMovieLng(String movieLng) {
        this.movieLng = movieLng;
    }


    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public String getMovieUid() {
        return movieUid;
    }

    public void setMovieUid(String movieUid) {
        this.movieUid = movieUid;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }
}
