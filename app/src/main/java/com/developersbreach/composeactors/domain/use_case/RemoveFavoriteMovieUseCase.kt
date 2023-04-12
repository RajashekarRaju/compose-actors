package com.developersbreach.composeactors.domain.use_case

import com.developersbreach.composeactors.data.model.Movie
import com.developersbreach.composeactors.data.repository.movie.MovieRepository
import javax.inject.Inject

class RemoveFavoriteMovieUseCase @Inject constructor (private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) {
        movieRepository.deleteSelectedFavoriteMovie(
            movie
        )
    }
}