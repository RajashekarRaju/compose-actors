package com.developersbreach.composeactors.data.model

enum class BottomSheetType(var movieOrPersonId: Int? = null) {
    MovieDetailBottomSheet(movieOrPersonId = null),
    ActorDetailBottomSheet(movieOrPersonId = null)
}