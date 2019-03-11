package com.example.tikaltask.paging

import android.annotation.SuppressLint
import android.arch.paging.PageKeyedDataSource
import com.example.tikaltask.repository.MoviesRepository
import com.example.tikaltask.repository.model.Movie
import com.example.tikaltask.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class MoviesPageKeyedDataSource(val repository: MoviesRepository,
                                private val mCompositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, Movie>(){

    companion object {
        private const val CONNECTION_FAILED = "Connection Failed"
    }

    val networkLiveData : SingleLiveEvent<String> = SingleLiveEvent()
    private var mPageIndex = 1

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        repository.getMovies(mPageIndex).doOnSubscribe { disposable ->
            mCompositeDisposable.add(disposable)
        }.subscribe ({
            if(!it.isEmpty()){
                callback.onResult(it, null, mPageIndex + 1)
            }
        },{throwable ->
            if(throwable is UnknownHostException) {
                networkLiveData.postValue(CONNECTION_FAILED)
            }
        })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        repository.getMovies(params.key).doOnSubscribe {disposable ->
            mCompositeDisposable.add(disposable)
        }.subscribe ({
            if(!it.isEmpty()) {
                callback.onResult(it, params.key + 1)
            }
        },{throwable ->
            if(throwable is UnknownHostException) {
                networkLiveData.postValue(CONNECTION_FAILED)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}