package com.developersbreach.composeactors.repository

import com.developersbreach.composeactors.repository.database.DatabaseRepository
import com.developersbreach.composeactors.repository.network.NetworkRepository

class AppRepository(
    networkRepository: NetworkRepository,
    databaseRepository: DatabaseRepository
) {
    val getNetworkRepository = networkRepository
    val getDatabaseRepository = databaseRepository
}