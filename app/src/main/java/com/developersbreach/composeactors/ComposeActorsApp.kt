package com.developersbreach.composeactors

import android.app.Application
import com.developersbreach.composeactors.repository.AppRepository
import com.developersbreach.composeactors.repository.database.DatabaseRepository
import com.developersbreach.composeactors.repository.database.getDatabaseInstance
import com.developersbreach.composeactors.repository.network.NetworkDataSource
import com.developersbreach.composeactors.repository.network.NetworkRepository
import timber.log.Timber

class ComposeActorsApp : Application() {

    lateinit var repository: AppRepository

    override fun onCreate() {
        super.onCreate()

        initializeRepository()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeRepository() {
        val networkDataSource = NetworkDataSource()
        val databaseDataSource = getDatabaseInstance(applicationContext)
        val networkRepository = NetworkRepository(networkDataSource)
        val databaseRepository = DatabaseRepository(databaseDataSource)
        repository = AppRepository(networkRepository, databaseRepository)
    }
}