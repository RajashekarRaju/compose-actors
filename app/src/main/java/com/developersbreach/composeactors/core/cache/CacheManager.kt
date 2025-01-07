package com.developersbreach.composeactors.core.cache

import android.util.LruCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheManager @Inject constructor(
    cacheSize: Int = 100
) {
    private val cache = object : LruCache<String, Any>(cacheSize) {
        override fun sizeOf(key: String, value: Any): Int {
            return 1
        }
    }

    @Synchronized
    fun <T> get(key: String): T? {
        return cache[key] as? T
    }

    @Synchronized
    fun <T> put(key: String, value: T) {
        cache.put(key, value)
    }

    @Synchronized
    fun remove(key: String) {
        cache.remove(key)
    }
}