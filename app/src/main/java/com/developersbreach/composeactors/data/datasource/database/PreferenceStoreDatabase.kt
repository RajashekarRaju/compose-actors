package com.developersbreach.composeactors.data.datasource.database

import android.content.Context
import com.developersbreach.composeactors.utils.PreferenceConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceStoreDatabase @Inject constructor(@ApplicationContext context: Context){

    private val sharedPreferences =
        context.applicationContext.getSharedPreferences("compose_actors", Context.MODE_PRIVATE)

    fun getRegion(): String? {
        return sharedPreferences.getString(PreferenceConstants.USER_REGION, null)
    }

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
}