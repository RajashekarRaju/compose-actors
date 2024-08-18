package com.developersbreach.composeactors.data.repository.user

import androidx.compose.runtime.MutableState
import com.developersbreach.composeactors.data.datasource.database.PreferenceStoreDatabase
import com.developersbreach.composeactors.utils.PreferenceConstants
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val preferenceStoreDatabase: PreferenceStoreDatabase) {

    fun setRegion(countryCode: String, setRegionSuccessesCallBack: MutableState<Boolean>): Boolean {
        return if (countryCode != getRegion()) {
            preferenceStoreDatabase.setString(
                PreferenceConstants.USER_REGION,
                countryCode
            )
            setRegionSuccessesCallBack.value = true
            true
        } else false
    }

    fun setDefaultRegion() {
        if (getRegion() == null) {
            preferenceStoreDatabase.setString(
                PreferenceConstants.USER_REGION,
                Locale.getDefault().country
            )
        }
    }

    fun getRegion(): String? {
        return preferenceStoreDatabase.getRegion()
    }
}