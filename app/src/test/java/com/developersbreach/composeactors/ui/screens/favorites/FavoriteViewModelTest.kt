package com.developersbreach.composeactors.ui.screens.favorites

import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val personRepository: PersonRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = FavoriteViewModel(
            movieRepository = movieRepository,
            personRepository = personRepository,
        )
    }

    @Test
    fun `upon calling removeMovieFromFavorites calls deleteSelectedFavoriteMovie`() {
        viewModel.removeMovieFromFavorites(mockk())

        coVerify(exactly = 1) {
            movieRepository.deleteSelectedFavoriteMovie(any())
        }
    }

    @Test
    fun `upon calling removePersonFromFavorites calls deleteSelectedFavoritePerson`() {
        viewModel.removePersonFromFavorites(mockk())

        coVerify(exactly = 1) {
            personRepository.deleteSelectedFavoritePerson(any())
        }
    }
}