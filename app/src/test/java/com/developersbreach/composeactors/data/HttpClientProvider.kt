package com.developersbreach.composeactors.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Url
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {

    private fun mockEngine(
        url: Url,
        response: String,
    ): MockEngine {
        return MockEngine {
            if (it.url == url) {
                respond(
                    content = response,
                    status = HttpStatusCode.OK,
                    headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString())),
                )
            } else {
                respondError(HttpStatusCode.NotFound)
            }
        }
    }

    fun createHttpClient(
        expectedUrl: Url,
        response: String,
    ): HttpClient {
        val engine = mockEngine(expectedUrl, response)
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
}