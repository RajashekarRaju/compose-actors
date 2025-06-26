package com.developersbreach.composeactors.ui.screens.movieDetail

import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MovieDetailsData(
    val movieData: MovieDetail? = null,
    val similarMovies: List<Movie> = emptyList(),
    val recommendedMovies: List<Movie> = emptyList(),
    val movieCast: List<Cast> = emptyList(),
    val isMovieInWatchlist: Flow<Boolean> = flow { emit(false) },
    val movieProviders: List<Flatrate> = emptyList(),
    val selectedPersonDetails: PersonDetail? = null,
    val selectedMovieDetails: MovieDetail? = null,
)