package com.maxlab.darkmovies.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MovieListModal {

    public MovieListModal(String movieListName, List<MovieModel> movieModels) {
        this.movieListName = movieListName;
        this.movieModels = movieModels;
    }

    String movieListName;
    List<MovieModel> movieModels = new ArrayList<>();


    public MovieListModal() {
    }

    public String getMovieListName() {
        return movieListName;
    }

    public void setMovieListName(String movieListName) {
        this.movieListName = movieListName;
    }

    public List<MovieModel> getMovieModels() {
        return movieModels;
    }

    public void setMovieModels(List<MovieModel> movieModels) {
        this.movieModels = movieModels;
    }
}
