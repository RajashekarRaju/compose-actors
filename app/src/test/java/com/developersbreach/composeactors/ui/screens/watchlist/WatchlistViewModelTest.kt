package com.developersbreach.composeactors.ui.screens.watchlist

import arrow.core.Either
import com.developersbreach.composeactors.data.watchlist.model.WatchlistPerson
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.domain.core.ErrorReporter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WatchlistViewModelTest {

    private lateinit var viewModel: WatchlistViewModel
    private val watchlistRepository: WatchlistRepository = mockk(relaxed = true)
    private val errorReporter: ErrorReporter = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = WatchlistViewModel(
            watchlistRepository = watchlistRepository,
            errorReporter = errorReporter,
        )
    }

    @Test
    fun `upon calling removeMovieFromWatchlist calls deleteSelectedMovieFromWatchlist`() {
        viewModel.removeMovieFromWatchlist(mockk())

        coVerify(exactly = 1) {
            watchlistRepository.removeMovieFromWatchlist(any())
        }
    }

    @Test
    fun `upon calling removePersonFromWatchlist calls deleteSelectedPersonFromWatchlist`() {
        val person = mockk<WatchlistPerson>()
        every { person.personId } returns 1

        coEvery {
            watchlistRepository.removePersonFromWatchlist(1)
        } returns Either.Right(Unit)

        viewModel.removePersonFromWatchlist(person)

        coVerify(exactly = 1) {
            watchlistRepository.removePersonFromWatchlist(1)
        }
    }
}