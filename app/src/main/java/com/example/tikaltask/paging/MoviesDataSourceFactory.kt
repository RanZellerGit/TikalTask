package com.example.tikaltask.paging

import android.arch.paging.DataSource
import com.example.tikaltask.repository.MoviesRepository
import com.example.tikaltask.repository.model.Movie
import io.reactivex.disposables.CompositeDisposable


class MoviesDataSourceFactory(
    val repository: MoviesRepository,
    compositeDisposable: CompositeDisposable) : DataSource.Factory<Int, Movie>() {

    val dataSource : MoviesPageKeyedDataSource = MoviesPageKeyedDataSource(repository, compositeDisposable)

    override fun create(): DataSource<Int, Movie> {
        return dataSource
    }

}