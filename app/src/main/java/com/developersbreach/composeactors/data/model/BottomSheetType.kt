package com.developersbreach.composeactors.data.model

enum class BottomSheetType(var movieOrActorId: Int? = null) {
    MovieDetailBottomSheet(movieOrActorId = null),
    ActorDetailBottomSheet(movieOrActorId = null)
}