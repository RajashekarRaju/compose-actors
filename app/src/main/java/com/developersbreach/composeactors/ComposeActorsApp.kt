@file:Suppress("unused")

package com.developersbreach.composeactors

import android.app.Application
import com.developersbreach.composeactors.diKoin.databaseModule
import com.developersbreach.composeactors.diKoin.networkDataSourceModule
import com.developersbreach.composeactors.diKoin.repositoryModule
import com.developersbreach.composeactors.diKoin.viewModelModule
import com.developersbreach.composeactors.navigation.AppNavigation
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Only place where the repository has been initialized and passed across all viewModels.
 * This instance property is used in [AppNavigation] class for all ViewModels to access app data.
 */
class ComposeActorsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ComposeActorsApp)
            modules(
                networkDataSourceModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}