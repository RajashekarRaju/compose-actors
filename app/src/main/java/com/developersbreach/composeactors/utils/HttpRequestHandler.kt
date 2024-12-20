package com.developersbreach.composeactors.utils

import com.developersbreach.composeactors.data.PagedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpRequestHandler @Inject constructor(
    val client: HttpClient,
) {
    suspend inline fun <reified T> getResponse(url: URL): T {
        return try {
            client.get(url).body<T>()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend inline fun <reified T> getPagedResponse(url: URL): PagedResponse<T> {
        return try {
            client.get(url).body<PagedResponse<T>>()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}