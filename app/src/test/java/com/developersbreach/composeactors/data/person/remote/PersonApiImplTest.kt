package com.developersbreach.composeactors.data.person.remote

import arrow.core.Either
import com.developersbreach.composeactors.core.cache.CacheManager
import com.developersbreach.composeactors.core.network.BaseUrlProvider.Companion.API_KEY
import com.developersbreach.composeactors.core.network.BaseUrlProvider.Companion.BASE_URL
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.HttpClientProvider.createHttpClient
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.datasource.fake.fakeMovieList
import com.developersbreach.composeactors.data.datasource.fake.fakePersonDetail
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.data.movie.model.MoviesResponse
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.repository.PersonRepositoryImpl
import io.ktor.http.Url
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PersonApiImplTest {

    private lateinit var personRepositoryImpl: PersonRepositoryImpl
    private val personApi: PersonApi = mockk()
    private val databaseDataSource: DatabaseDataSource = mockk()
    private val cacheManager: CacheManager = mockk()

    private val moviesResponse = MoviesResponse(fakeMovieList())
    private val pagedResponse: PagedResponse<Person> = PagedResponse(
        totalPages = 1,
        page = 1,
        data = fakePersonsList(),
    )

    @Before
    fun setup() {
        personRepositoryImpl = PersonRepositoryImpl(
            personApi = personApi,
            databaseDataSource = databaseDataSource,
            cacheManager = cacheManager,
        )
    }

    @Test
    fun `getPopularPersons should return expected response`() = runTest {
        val client = createHttpClient(
            expectedUrl = Url("${BASE_URL}person/popular?$API_KEY"),
            response = Json.encodeToJsonElement(pagedResponse).toString(),
        )

        val requestHandler = HttpRequestHandler(client)
        val result = PersonApiImpl(requestHandler).getPopularPersons()

        assertEquals(Either.Right(pagedResponse), result)
    }

    @Test
    fun `getTrendingPersons should return expected response`() = runTest {
        val client = createHttpClient(
            expectedUrl = Url("${BASE_URL}trending/person/week?$API_KEY"),
            response = Json.encodeToJsonElement(pagedResponse).toString(),
        )

        val requestHandler = HttpRequestHandler(client)
        val result = PersonApiImpl(requestHandler).getTrendingPersons()

        assertEquals(Either.Right(pagedResponse), result)
    }

    @Test
    fun `getPersonDetails should return expected response`() = runTest {
        val client = createHttpClient(
            expectedUrl = Url("${BASE_URL}person/1?$API_KEY"),
            response = Json.encodeToJsonElement(fakePersonDetail).toString(),
        )

        val requestHandler = HttpRequestHandler(client)
        val result = PersonApiImpl(requestHandler).getPersonDetails(1)

        assertEquals(Either.Right(fakePersonDetail), result)
    }

    @Test
    fun `getCastDetails should return expected response`() = runTest {
        val client = createHttpClient(
            expectedUrl = Url("${BASE_URL}person/1/movie_credits?$API_KEY"),
            response = Json.encodeToJsonElement(moviesResponse).toString(),
        )

        val requestHandler = HttpRequestHandler(client)
        val result = PersonApiImpl(requestHandler).getCastDetails(1)

        assertEquals(Either.Right(moviesResponse), result)
    }
}