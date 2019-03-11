package com.example.tikaltask

import android.app.Application
import com.example.tikaltask.di.applicationModule
import org.koin.android.ext.android.startKoin

class TikalTaskApp : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule))
    }
}