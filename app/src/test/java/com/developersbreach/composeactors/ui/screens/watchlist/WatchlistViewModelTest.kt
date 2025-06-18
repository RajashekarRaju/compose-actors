package com.developersbreach.composeactors.ui.screens.watchlist

import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import io.mockk.coVerify
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
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val personRepository: PersonRepository = mockk(relaxed = true)
    private val watchlistRepository: WatchlistRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = WatchlistViewModel(
            movieRepository = movieRepository,
            personRepository = personRepository,
            watchlistRepository = watchlistRepository,
        )
    }

    @Test
    fun `upon calling removeMovieFromWatchlist calls deleteSelectedMovieFromWatchlist`() {
        viewModel.removeMovieFromWatchlist(mockk())

        coVerify(exactly = 1) {
            movieRepository.deleteSelectedMovieFromWatchlist(any())
        }
    }

    @Test
    fun `upon calling removePersonFromWatchlist calls deleteSelectedPersonFromWatchlist`() {
        viewModel.removePersonFromWatchlist(mockk())

        coVerify(exactly = 1) {
            personRepository.deleteSelectedPersonFromWatchlist(any())
        }
    }
}