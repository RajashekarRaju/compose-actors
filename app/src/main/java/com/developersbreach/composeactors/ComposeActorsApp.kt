package com.developersbreach.composeactors

import android.app.Application
import com.developersbreach.composeactors.navigation.AppNavigation
import com.developersbreach.composeactors.repository.AppRepository
import timber.log.Timber

class ComposeActorsApp : Application() {

    /**
     * Only place where the repository has been initialized and used in app.
     * This property is used in [AppNavigation] for all ViewModels to access app data.
     */
    lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()

        repository = AppRepository()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}