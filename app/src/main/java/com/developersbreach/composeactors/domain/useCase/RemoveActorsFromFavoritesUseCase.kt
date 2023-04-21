package com.developersbreach.composeactors.domain.useCase

import com.developersbreach.composeactors.data.model.FavoriteActor
import com.developersbreach.composeactors.data.repository.actor.ActorRepository
import javax.inject.Inject

class RemoveActorsFromFavoritesUseCase @Inject constructor(
    private val actorRepository: ActorRepository
) {
    suspend operator fun invoke(favoriteActor: FavoriteActor) {
        actorRepository.deleteSelectedFavoriteActor(
            favoriteActor = favoriteActor
        )
    }
}