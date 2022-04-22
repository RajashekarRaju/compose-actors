package com.developersbreach.composeactors.diKoin

import com.developersbreach.composeactors.repository.database.DatabaseRepository
import com.developersbreach.composeactors.repository.database.getDatabaseInstance
import com.developersbreach.composeactors.repository.network.NetworkDataSource
import com.developersbreach.composeactors.repository.network.NetworkRepository
import com.developersbreach.composeactors.ui.actorDetails.ActorDetailsViewModel
import com.developersbreach.composeactors.ui.home.HomeViewModel
import com.developersbreach.composeactors.ui.movieDetail.MovieDetailViewModel
import com.developersbreach.composeactors.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkDataSourceModule = module {
    single {
        NetworkDataSource()
    }
}

val databaseModule = module {
    single {
        getDatabaseInstance(get())
    }
}

val repositoryModule = module {

    single {
        NetworkRepository(get())
    }

    single {
        DatabaseRepository(get())
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