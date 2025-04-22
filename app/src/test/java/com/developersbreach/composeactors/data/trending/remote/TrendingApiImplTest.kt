package com.developersbreach.composeactors.data.trending.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.network.BaseUrlProvider.Companion.API_KEY
import com.developersbreach.composeactors.core.network.BaseUrlProvider.Companion.BASE_URL
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.HttpClientProvider.createHttpClient
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.data.person.model.Person
import io.ktor.http.Url
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TrendingApiImplTest {

    private val pagedResponse: PagedResponse<Person> = PagedResponse(
        totalPages = 1,
        page = 1,
        data = fakePersonsList(),
    )

    @Test
    fun `getTrendingActors should return expected response`() = runTest {
        val client = createHttpClient(
            expectedUrl = Url("${BASE_URL}trending/person/week?${API_KEY}"),
            response = Json.encodeToJsonElement(pagedResponse).toString(),
        )

        val requestHandler = HttpRequestHandler(client)
        val result = TrendingApiImpl(requestHandler).getTrendingActors()

        assertEquals(Either.Right(pagedResponse), result)
    }

    @Test
    fun `getTrendingActors should return error when no response is returned`() = runTest {
        val client = createHttpClient(
            expectedUrl = Url("${BASE_URL}trending/person/week?${API_KEY}"),
            response = Json.encodeToJsonElement("").toString(),
        )

        val requestHandler = HttpRequestHandler(client)
        val result = TrendingApiImpl(requestHandler).getTrendingActors()

        assertTrue(result is Either.Left)
    }
}