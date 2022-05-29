package com.developersbreach.composeactors.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.developersbreach.composeactors.repository.database.DatabaseRepository
import com.developersbreach.composeactors.repository.network.NetworkRepository
import com.developersbreach.composeactors.ui.actorDetails.ActorDetailsViewModel
import com.developersbreach.composeactors.ui.home.HomeViewModel
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailViewModel
import com.developersbreach.composeactors.ui.search.SearchViewModel


fun viewModelFactory(
    application: Application,
    networkRepo: NetworkRepository,
    databaseRepo: DatabaseRepository,
    id: Int = 0
): ViewModelProvider.AndroidViewModelFactory {
    return object : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when {
                modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(application, networkRepo, databaseRepo) as T
                }
                modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                    SearchViewModel(application, networkRepo) as T
                }
                modelClass.isAssignableFrom(ActorDetailsViewModel::class.java) -> {
                    ActorDetailsViewModel(application, id, networkRepo) as T
                }
                modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                    MovieDetailViewModel(application, id, networkRepo, databaseRepo) as T
                }
                else -> throw IllegalArgumentException("Cannot create Instance for $modelClass")
            }
        }
    }
}