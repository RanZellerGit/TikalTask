package com.example.tikaltask.di

import android.arch.persistence.room.Room
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.tikaltask.Constants
import com.example.tikaltask.paging.MoviesDataSourceFactory
import com.example.tikaltask.repository.MoviesRepository
import com.example.tikaltask.repository.localReposotory.MoviesDao
import com.example.tikaltask.repository.localReposotory.MoviesDatabase
import com.example.tikaltask.repository.localReposotory.MoviesLocalRepo
import com.example.tikaltask.repository.remotRepository.MoviesRemoteRepo
import com.example.tikaltask.repository.remotRepository.TmdbApi
import com.example.tikaltask.utils.ConnectivityLiveData
import com.example.tikaltask.viewModel.MoviesViewModel
import io.reactivex.disposables.CompositeDisposable
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    // Local Repo
    single<MoviesDao> { Room.databaseBuilder(androidContext(), MoviesDatabase::class.java, "db.name").build().getMoviesDao() }
    single { MoviesLocalRepo(get()) }

    // Remote Repo
    single<TmdbApi> {
        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("API", it)
        })
        logger.level = HttpLoggingInterceptor.Level.BASIC

        Retrofit.Builder()
            .baseUrl(HttpUrl.parse(Constants.BASE_URL)!!)
            .client(OkHttpClient.Builder().addInterceptor(logger).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }


    single { MoviesRemoteRepo(get()) }

    // Repository
    single { MoviesRepository(get(), get()) }

    single { CompositeDisposable() }

    single<ConnectivityLiveData>{ ConnectivityLiveData(androidContext().getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    ) }

    // ViewModel
    viewModel{ MoviesViewModel(MoviesDataSourceFactory(get(), get()) ,get(), get())}

}
