package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.core.cache.CacheManager
import com.developersbreach.composeactors.core.database.AppDatabase
import com.developersbreach.composeactors.core.network.HttpRequestHandler
import com.developersbreach.composeactors.data.auth.AuthenticationService
import com.developersbreach.composeactors.data.datasource.database.DatabaseDataSource
import com.developersbreach.composeactors.data.movie.remote.MovieApi
import com.developersbreach.composeactors.data.movie.remote.MovieApiImpl
import com.developersbreach.composeactors.data.movie.repository.MovieRepository
import com.developersbreach.composeactors.data.movie.repository.MovieRepositoryImpl
import com.developersbreach.composeactors.data.person.remote.PersonApi
import com.developersbreach.composeactors.data.person.remote.PersonApiImpl
import com.developersbreach.composeactors.data.person.repository.PersonRepository
import com.developersbreach.composeactors.data.person.repository.PersonRepositoryImpl
import com.developersbreach.composeactors.data.search.remote.SearchApi
import com.developersbreach.composeactors.data.search.remote.SearchApiImpl
import com.developersbreach.composeactors.data.search.repository.SearchRepository
import com.developersbreach.composeactors.data.search.repository.SearchRepositoryImpl
import com.developersbreach.composeactors.data.trending.remote.TrendingApi
import com.developersbreach.composeactors.data.trending.remote.TrendingApiImpl
import com.developersbreach.composeactors.data.trending.repository.TrendingRepository
import com.developersbreach.composeactors.data.trending.repository.TrendingRepositoryImpl
import com.developersbreach.composeactors.data.watchlist.remote.WatchlistApi
import com.developersbreach.composeactors.data.watchlist.remote.WatchlistApiImpl
import com.developersbreach.composeactors.data.watchlist.paging.WatchlistRemoteMediator
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepository
import com.developersbreach.composeactors.data.watchlist.repository.WatchlistRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideTrendingApi(
        requestHandler: HttpRequestHandler,
    ): TrendingApi {
        return TrendingApiImpl(requestHandler)
    }

    @Provides
    @Singleton
    fun provideTrendingRepository(
        trendingApi: TrendingApi,
    ): TrendingRepository {
        return TrendingRepositoryImpl(trendingApi)
    }

    @Provides
    @Singleton
    fun provideSearchApiImpl(
        requestHandler: HttpRequestHandler,
    ): SearchApi {
        return SearchApiImpl(requestHandler)
    }

    @Provides
    @Singleton
    fun provideWatchlistApiImpl(
        requestHandler: HttpRequestHandler,
    ): WatchlistApi {
        return WatchlistApiImpl(requestHandler)
    }

    @Provides
    @Singleton
    fun provideWatchlistRepository(
        watchlistApi: WatchlistApi,
        watchlistRemoteMediator: WatchlistRemoteMediator,
        database: AppDatabase,
        authenticationService: AuthenticationService,
    ): WatchlistRepository {
        return WatchlistRepositoryImpl(
            watchlistApi = watchlistApi,
            watchlistRemoteMediator = watchlistRemoteMediator,
            database = database,
            authenticationService = authenticationService,
        )
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchApi: SearchApi,
    ): SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }

    @Provides
    @Singleton
    fun provideMovieApiImpl(
        requestHandler: HttpRequestHandler,
    ): MovieApi {
        return MovieApiImpl(requestHandler)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApi: MovieApi,
    ): MovieRepository {
        return MovieRepositoryImpl(movieApi)
    }

    @Provides
    @Singleton
    fun providePersonApiImpl(
        requestHandler: HttpRequestHandler,
    ): PersonApi {
        return PersonApiImpl(requestHandler)
    }

    @Provides
    @Singleton
    fun providePersonRepository(
        personApi: PersonApi,
        databaseDataSource: DatabaseDataSource,
        cacheManager: CacheManager,
    ): PersonRepository {
        return PersonRepositoryImpl(personApi, databaseDataSource, cacheManager)
    }
}