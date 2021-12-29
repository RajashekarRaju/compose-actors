package com.developersbreach.composeactors

import android.app.Application
import com.developersbreach.composeactors.data.NetworkDataSource
import com.developersbreach.composeactors.navigation.AppNavigation
import com.developersbreach.composeactors.repository.AppRepository
import timber.log.Timber

/**
 * Only place where the repository has been initialized and passed across all viewModels.
 * This instance property is used in [AppNavigation] class for all ViewModels to access app data.
 */
class ComposeActorsApp : Application() {

    // Only place where the repository has been initialized and passed across all viewModels
    lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()

        // This is the only data source for whole app.
        val networkDataSource = NetworkDataSource()
        repository = AppRepository(networkDataSource)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}