package com.developersbreach.composeactors.data.datasource.fake

import com.developersbreach.composeactors.data.person.model.Person
import com.developersbreach.composeactors.data.person.model.PersonDetail
import com.developersbreach.composeactors.data.movie.model.Cast
import com.developersbreach.composeactors.data.person.model.FavoritePerson

private val personsData = listOf(
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

fun fakePersonsList(): MutableList<Person> {
    val persons = mutableListOf<Person>()
    personsData.forEachIndexed { index, name ->
        persons.add(Person(index, name, ""))
    }
    return persons
}

fun fakeFavoritePersonsList(): MutableList<FavoritePerson> {
    val actors = mutableListOf<FavoritePerson>()
    personsData.forEachIndexed { index, name ->
        actors.add(FavoritePerson(index, name, "", "Berlin"))
    }
    return actors
}

fun fakeMovieCastList(): MutableList<Cast> {
    val cast = mutableListOf<Cast>()
    personsData.forEachIndexed { index, name ->
        cast.add(Cast(index, name, ""))
    }
    return cast
}

val popularPersonLists = listOf(
    Person(
        personId = 28782,
        personName = "Monica Bellucci",
        profilePath = "z3sLuRKP7hQVrvSTsqdLjGSldwG.jpg",
    ),
    Person(
        personId = 287,
        personName = "Brad Pitt",
        profilePath = "kU3B75TyRiCgE270EyZnHjfivoq.jpg",
    ),
)

val trendingPersonLists = listOf(
    Person(
        personId = 8784,
        personName = "Daniel Craig",
        profilePath = "rFuETZeyOAfIqBahOObF7Soq5Dh.jpg",
    ),
    Person(
        personId = 1892,
        personName = "Matt Damon",
        profilePath = "7wbHIn7GziFlJLPl8Zu1XVl24EG.jpg",
    ),
)

val fakePersonDetail = PersonDetail(
    personId = 28782,
    personName = "Monica Bellucci",
    profilePath = "z3sLuRKP7hQVrvSTsqdLjGSldwG.jpg",
    biography = "This is fake biography for the actor added here to see how the actual preview looks in the screen so don't get any serious about this.",
    dateOfBirth = "59",
    placeOfBirth = "Italy",
    popularity = 43.0,
)