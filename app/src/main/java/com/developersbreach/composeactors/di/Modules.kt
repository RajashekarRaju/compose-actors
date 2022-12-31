package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.data.repository.DatabaseRepository
import com.developersbreach.composeactors.data.datasource.database.getDatabaseInstance
import com.developersbreach.composeactors.data.repository.NetworkRepository
import com.developersbreach.composeactors.ui.screens.actorDetails.ActorDetailsViewModel
import com.developersbreach.composeactors.ui.screens.home.HomeViewModel
import com.developersbreach.composeactors.ui.screens.movieDetail.MovieDetailViewModel
import com.developersbreach.composeactors.ui.screens.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        getDatabaseInstance(get())
    }
}

val repositoryModule = module {
    single {
        NetworkRepository()
    }

    single {
        DatabaseRepository(get())
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        ActorDetailsViewModel(get(), get())
    }
    viewModel {
        MovieDetailViewModel(get(), get(), get())
    }
}