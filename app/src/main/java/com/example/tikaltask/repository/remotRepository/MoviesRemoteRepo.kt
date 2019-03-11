package com.example.tikaltask.repository.remotRepository

import com.example.tikaltask.Constants
import com.example.tikaltask.repository.model.Movie
import io.reactivex.Observable

class MoviesRemoteRepo(val api : TmdbApi) {

    fun getMovies(page : Int) : Observable<List<Movie>> {
        return this.api.getMovies(Constants.API_KEY,Constants.LANGUAGE, page).flatMap {
            it.results.forEachIndexed { index, movie ->
                movie.index = index + (Constants.PAGE_SIZE * (page - 1))
            }
            Observable.just(it.results)
        }
    }
}