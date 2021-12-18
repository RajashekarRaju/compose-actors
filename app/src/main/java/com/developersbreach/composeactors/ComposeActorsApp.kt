package com.developersbreach.composeactors

import android.app.Application
import com.developersbreach.composeactors.repository.AppRepository
import timber.log.Timber

class ComposeActorsApp : Application() {

    lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()

        repository = AppRepository()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}