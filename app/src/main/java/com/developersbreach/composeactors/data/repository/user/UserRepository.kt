package com.developersbreach.composeactors.data.repository.user

import com.developersbreach.composeactors.data.datasource.database.PreferenceStoreDatabase
import com.developersbreach.composeactors.utils.PreferenceConstants
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val preferenceStoreDatabase: PreferenceStoreDatabase) {

    fun setRegion() {
        if (preferenceStoreDatabase.getRegion() == null) {
            preferenceStoreDatabase.setString(
                PreferenceConstants.USER_REGION,
                Locale.getDefault().country
            )
        }
    }
}