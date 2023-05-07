package com.developersbreach.composeactors.data.datasource.fake

import com.developersbreach.composeactors.data.model.Actor
import com.developersbreach.composeactors.data.model.ActorDetail
import com.developersbreach.composeactors.data.model.Cast
import com.developersbreach.composeactors.data.model.FavoriteActor

private val actorsData = listOf(
    "John travolta",
    "Samuel L Jackson",
    "Uma Thurman",
    "Bruce Willis",
    "Ving Rhames",
    "Harvey Keitel",
    "Tim Roth",
    "Amanda Plummer",
    "Quentin Tarantino",
    "Christopher Walken",
    "Eric Stoltz",
    "Maria de Medeiros",
    "Rosanna Arquette",
    "Sophia Lillis",
)

fun fakeActorsList(): MutableList<Actor> {
    val actors = mutableListOf<Actor>()
    actorsData.forEachIndexed { index, name ->
        actors.add(Actor(index, name, ""))
    }
    return actors
}

fun fakeFavoriteActorsList(): MutableList<FavoriteActor> {
    val actors = mutableListOf<FavoriteActor>()
    actorsData.forEachIndexed { index, name ->
        actors.add(FavoriteActor(index, name, "", "Berlin"))
    }
    return actors
}

fun fakeMovieCastList(): MutableList<Cast> {
    val cast = mutableListOf<Cast>()
    actorsData.forEachIndexed { index, name ->
        cast.add(Cast(index, name, ""))
    }
    return cast
}

val popularActorList = listOf(
    Actor(
        actorId = 28782,
        actorName = "Monica Bellucci",
        profileUrl = "z3sLuRKP7hQVrvSTsqdLjGSldwG.jpg"
    ),
    Actor(
        actorId = 287,
        actorName = "Brad Pitt",
        profileUrl = "kU3B75TyRiCgE270EyZnHjfivoq.jpg"
    )
)

val trendingActorList = listOf(
    Actor(
        actorId = 8784,
        actorName = "Daniel Craig",
        profileUrl = "rFuETZeyOAfIqBahOObF7Soq5Dh.jpg"
    ),
    Actor(
        actorId = 1892,
        actorName = "Matt Damon",
        profileUrl = "7wbHIn7GziFlJLPl8Zu1XVl24EG.jpg"
    ),
)

val fakeActorDetail = ActorDetail(
    actorId = 28782,
    actorName = "Monica Bellucci",
    profileUrl = "z3sLuRKP7hQVrvSTsqdLjGSldwG.jpg",
    biography = "This is fake biography for the actor added here to see how the actual preview looks in the screen so don't get any serious about this.",
    dateOfBirth = "59",
    placeOfBirth = "Italy",
    popularity = 43.0
)