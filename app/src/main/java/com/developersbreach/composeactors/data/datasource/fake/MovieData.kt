package com.developersbreach.composeactors.data.datasource.fake

import com.developersbreach.composeactors.data.movie.model.Genre
import com.developersbreach.composeactors.data.movie.model.Movie
import com.developersbreach.composeactors.data.movie.model.MovieDetail
import com.developersbreach.composeactors.data.movie.model.ProductionCompanies

fun fakeMovieList(): MutableList<Movie> {
    val movies = mutableListOf<Movie>()
    listOf(
        "Pulp Fiction",
        "Kill Bill: Volume 1",
        "Taxi Driver",
        "Goodfellas",
        "Kill Bill: Volume 2",
        "Heat",
        "Scarface",
        "Scent of a Woman",
        "The Devil's Advocate",
        "The Prestige",
        "Prisoners",
        "American Psycho",
        "Logan",
    ).forEachIndexed { index, name ->
        movies.add(Movie(index, name, "", ""))
    }

    return movies
}

val upcomingMoviesList = listOf(
    Movie(
        movieId = 363736,
        movieTitle = "Oppenheimer",
        posterPathUrl = "",
        backdropPathUrl = "",
    ),
    Movie(
        movieId = 123434,
        movieTitle = "Dune",
        posterPathUrl = "",
        backdropPathUrl = "",
    ),
)

val nowPlayingMoviesList = listOf(
    Movie(
        movieId = 157336,
        movieTitle = "Interstellar",
        posterPathUrl = "",
        backdropPathUrl = "",
    ),
    Movie(
        movieId = 244786,
        movieTitle = "Whiplash",
        posterPathUrl = "",
        backdropPathUrl = "",
    ),
)

private val fakeProductionCompanies = listOf(
    ProductionCompanies(
        id = 7228,
        logoPath = "rrbt",
        name = "Bad movies company",
    ),
    ProductionCompanies(
        id = 7228,
        logoPath = "rrbt",
        name = "Silly company",
    ),
)

val fakeMovieDetail = MovieDetail(
    movieId = 12345,
    movieTitle = "Pulp Fiction",
    backdropPath = "banner-url.jpeg",
    budget = 20_00_000,
    genres = listOf(Genre(1, "Thriller"), Genre(2, "Crime")),
    originalLanguage = "English",
    overview = "In the realm of underworld, a series of incidents intertwines the lives of two Los Angeles mobsters, a gangster's wife, a boxer and two small-time criminals.",
    popularity = 99.0,
    posterPath = "poster-url.jpeg",
    productionCompanies = fakeProductionCompanies,
    releaseDate = "1994",
    revenue = 21400000,
    runtime = 154,
    status = "Released",
    tagline = "You won't know the facts until you've seen the fiction.",
    voteAverage = 99.0,
)