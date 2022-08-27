package com.developersbreach.composeactors.diKoin

import com.developersbreach.composeactors.datasource.DatabaseDataSource
import com.developersbreach.composeactors.repository.DatabaseRepository
import com.developersbreach.composeactors.data.database.getDatabaseInstance
import com.developersbreach.composeactors.datasource.NetworkDataSource
import com.developersbreach.composeactors.repository.NetworkRepository
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

    single {
        DatabaseDataSource(get())
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