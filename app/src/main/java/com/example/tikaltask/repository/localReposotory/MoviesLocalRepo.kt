package com.example.tikaltask.repository.localReposotory

import com.example.tikaltask.Constants
import com.example.tikaltask.repository.model.Movie
import io.reactivex.Observable


class MoviesLocalRepo(private val mMoviesDao: MoviesDao){

    fun getAllMovies(page : Int): Observable<List<Movie>> {
        return Observable.fromCallable {
            val tmp = mMoviesDao.getMovies((page - 1) * Constants.PAGE_SIZE, (page) * Constants.PAGE_SIZE)
            tmp
        }
    }

    fun insertMovies(movies : List<Movie>){
        mMoviesDao.insertMovie(movies)
    }
}