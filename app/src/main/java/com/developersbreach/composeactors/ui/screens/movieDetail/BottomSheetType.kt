package com.developersbreach.composeactors.ui.screens.movieDetail

enum class BottomSheetType(var movieOrPersonId: Int? = null) {
    MovieDetailBottomSheet(movieOrPersonId = null),
    ActorDetailBottomSheet(movieOrPersonId = null),
}