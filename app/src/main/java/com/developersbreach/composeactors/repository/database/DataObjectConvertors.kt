package com.developersbreach.composeactors.repository.database

import com.developersbreach.composeactors.model.Actor
import com.developersbreach.composeactors.model.Movie
import com.developersbreach.composeactors.repository.database.entity.FavoriteActorsEntity
import com.developersbreach.composeactors.repository.database.entity.FavoriteMoviesEntity


fun Actor.actorAsDatabaseModel(): FavoriteActorsEntity {
    return FavoriteActorsEntity(
        actorId = this.actorId,
        actorName = this.actorName,
        actorProfileUrl = this.profileUrl
    )
}

fun List<FavoriteActorsEntity>.actorAsDomainModel(): List<Actor> {
    return map {
        Actor(
            actorId = it.actorId,
            actorName = it.actorName,
            profileUrl = it.actorProfileUrl
        )
    }
}

fun Movie.movieAsDatabaseModel(): FavoriteMoviesEntity {
    return FavoriteMoviesEntity(
        movieId = this.movieId,
        moviePosterUrl = this.posterPathUrl,
    )
}

fun List<FavoriteMoviesEntity>.movieAsDomainModel(): List<Movie> {
    return map {
        Movie(
            movieId = it.movieId,
            posterPathUrl = it.moviePosterUrl
        )
    }
}