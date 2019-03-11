package com.example.tikaltask.paging

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.example.tikaltask.repository.MoviesRepository
import com.example.tikaltask.repository.model.Movie
import com.example.tikaltask.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class MoviesPageKeyedDataSource(val repository: MoviesRepository,
                                val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>(){

    val  ConnectionFailed = "Connection Failed"
    val networkLiveData : SingleLiveEvent<String> = SingleLiveEvent()
    private var pageIndex = 1

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        repository.getMovies(pageIndex).doOnSubscribe {disposable ->
            compositeDisposable.add(disposable)
        }.subscribe ({
            if(!it.isEmpty()){
                callback.onResult(it, null, pageIndex + 1)
            }
        },{throwable ->
            if(throwable is UnknownHostException) {
                networkLiveData.postValue(ConnectionFailed)
            }
        })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        repository.getMovies(params.key).doOnSubscribe {disposable ->
            compositeDisposable.add(disposable)
        }.subscribe ({
            if(!it.isEmpty()) {
                callback.onResult(it, params.key + 1)
            }
        },{throwable ->
            if(throwable is UnknownHostException) {
                networkLiveData.postValue(ConnectionFailed)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}