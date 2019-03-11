package com.example.tikaltask.repository

import com.example.tikaltask.repository.localReposotory.MoviesLocalRepo
import com.example.tikaltask.repository.model.Movie
import com.example.tikaltask.repository.remotRepository.MoviesRemoteRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MoviesRepository(private val mLocalRepo: MoviesLocalRepo, private val mRemoteRepo: MoviesRemoteRepo) {

    fun getMovies(page : Int): Observable<List<Movie>> {
        return Observable.mergeDelayError(
            mRemoteRepo.getMovies(page).doOnNext {
                mLocalRepo.insertMovies(it)}.subscribeOn(Schedulers.io()),
            mLocalRepo.getAllMovies(page).doOnNext{}.subscribeOn(Schedulers.io()))
    }
}