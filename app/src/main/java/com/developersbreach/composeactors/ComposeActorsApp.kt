package com.developersbreach.composeactors

import android.app.Application
import com.developersbreach.composeactors.repository.ActorsRepository
import timber.log.Timber

class ComposeActorsApp : Application() {

    lateinit var repository: ActorsRepository

    override fun onCreate() {
        super.onCreate()

        repository = ActorsRepository()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}