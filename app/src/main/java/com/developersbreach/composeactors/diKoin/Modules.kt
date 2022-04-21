package com.developersbreach.composeactors.diKoin

import com.developersbreach.composeactors.data.NetworkDataSource
import com.developersbreach.composeactors.repository.AppRepository
import com.developersbreach.composeactors.ui.actorDetails.ActorDetailsViewModel
import com.developersbreach.composeactors.ui.home.HomeViewModel
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailViewModel
import com.developersbreach.composeactors.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// This is the only data source for whole app.
val networkDataSourceModule = module {
    single {
        NetworkDataSource()
    }
}

val repositoryModule = module {
    single {
        AppRepository(get())
    }
}

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        ActorDetailsViewModel(get(), get())
    }
    viewModel {
        MovieDetailViewModel(get(), get())
    }
}