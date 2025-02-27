package com.developersbreach.composeactors.ui.screens.movieDetail

import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.movie.model.Flatrate
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.person.model.PersonDetail

data class MovieDetailsData(
    val movieData: MovieDetail? = null,
    val similarMovies: List<Movie> = emptyList(),
    val recommendedMovies: List<Movie> = emptyList(),
    val movieCast: List<Cast> = emptyList(),
    val isFetchingDetails: Boolean = false,
    val movieProviders: List<Flatrate> = emptyList()
)

data class ActorsSheetUIState(
    val selectedPersonDetails: PersonDetail? = null
)

data class MovieSheetUIState(
    val selectedMovieDetails: MovieDetail? = null
)
