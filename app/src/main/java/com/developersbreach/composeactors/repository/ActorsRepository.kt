package com.developersbreach.composeactors.repository

import com.developersbreach.composeactors.ui.details.Actor

class ActorsRepository {

    val actorsData by lazy {
        listOf(
            Actor(1, "Robert Di Nero"),
            Actor(2, "Al Pacino")
        )
    }

    fun getActor(
        actorId: Int
    ): Actor = actorsData.find {
        it.actorId == actorId
    }!!
}