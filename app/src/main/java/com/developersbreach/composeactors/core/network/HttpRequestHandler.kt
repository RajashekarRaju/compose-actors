package com.developersbreach.composeactors.core.network

import arrow.core.Either
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
    suspend inline fun <reified T> getResponse(url: URL): Either<Throwable, T> {
        return try {
            Either.Right(client.get(url).body<T>())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(e)
        }
    }

    suspend inline fun <reified T> getPagedResponse(url: URL): Either<Throwable, PagedResponse<T>> {
        return try {
            Either.Right(client.get(url).body<PagedResponse<T>>())
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(e)
        }
    }
}