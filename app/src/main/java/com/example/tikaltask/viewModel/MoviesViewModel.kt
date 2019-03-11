package com.example.tikaltask.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.tikaltask.Constants
import com.example.tikaltask.paging.MoviesDataSourceFactory
import com.example.tikaltask.repository.model.Movie
import com.example.tikaltask.utils.ConnectivityLiveData
import io.reactivex.disposables.CompositeDisposable


class MoviesViewModel(
    val moviesDataSourceFactory: MoviesDataSourceFactory,
    private val mCompositeDisposable : CompositeDisposable,
    val connectivityLiveData : ConnectivityLiveData) : ViewModel(){

    lateinit var movieListLiveDate : LiveData<PagedList<Movie>>
    private set

    val movieDetailsChosen : MutableLiveData<Movie> = MutableLiveData()
    var lastKnownNetworkStatus = true

    fun getNetworkLiveData() = moviesDataSourceFactory.dataSource.networkLiveData

    init {
        start()
    }

    fun start(){
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(Constants.PAGE_SIZE)
            .setPageSize(Constants.PAGE_SIZE).build()
        movieListLiveDate = LivePagedListBuilder(moviesDataSourceFactory, pagedListConfig).build()
    }

    fun updateMovieWasChosen(movie: Movie){
        movieDetailsChosen.postValue(movie)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }


}