package com.example.tikaltask

class Constants {
    companion object {
        val API_KEY = "95ae343da96f5b4147a1198ce2b13cba"
        val LANGUAGE = "en"
        val BASE_URL = "http://api.themoviedb.org/3/discover/movie/"

        val MOVIE_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/"
        val MOVIE_SMALL_IMAGE_URL_PREFIX = MOVIE_IMAGE_URL_PREFIX + "w300"
        val MOVIE_BIG_IMAGE_URL_PREFIX = MOVIE_IMAGE_URL_PREFIX + "w500"
        val PAGE_SIZE = 20
    }
}