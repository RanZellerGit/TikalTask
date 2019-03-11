package com.example.tikaltask.repository

import com.example.tikaltask.repository.localReposotory.MoviesLocalRepo
import com.example.tikaltask.repository.model.Movie
import com.example.tikaltask.repository.remotRepository.MoviesRemoteRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MoviesRepository(private val localRepo: MoviesLocalRepo, private val remoteRepo: MoviesRemoteRepo) {

    fun getMovies(page : Int): Observable<List<Movie>> {
        return Observable.mergeDelayError(
            remoteRepo.getMovies(page).doOnNext {
                localRepo.insertMovies(it)}.subscribeOn(Schedulers.io()),
            localRepo.getAllMovies(page).doOnNext{}.subscribeOn(Schedulers.io()))
    }
}