package com.developersbreach.composeactors.data.trending.repository

import arrow.core.Either
import com.developersbreach.composeactors.core.network.PagedResponse
import com.developersbreach.composeactors.data.datasource.fake.fakePersonsList
import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.trending.remote.TrendingApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TrendingRepositoryImplTest {

    private lateinit var trendingRepositoryImpl: TrendingRepositoryImpl
    private val trendingApi: TrendingApi = mockk()

    private val pagedResponse: PagedResponse<Person> = PagedResponse(
        totalPages = 1,
        page = 1,
        data = fakePersonsList(),
    )

    @Before
    fun setup() {
        trendingRepositoryImpl = TrendingRepositoryImpl(trendingApi)
    }

    @Test
    fun `getTrendingActors returns data when API returns successful response`() = runTest {
        coEvery {
            trendingApi.getTrendingActors()
        } returns Either.Right(pagedResponse)

        val expected = trendingRepositoryImpl.getTrendingActors()
        val result = trendingApi.getTrendingActors().map { it.data }

        assertEquals(expected, result)
    }

    @Test
    fun `getTrendingActors returns error when API returns error`() = runTest {
        val error = Either.Left(NullPointerException())
        coEvery {
            trendingApi.getTrendingActors()
        } returns error

        val result = trendingApi.getTrendingActors().map { it.data }

        assertEquals(error, result)
    }
}