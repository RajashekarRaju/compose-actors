package com.developersbreach.composeactors.di

import com.developersbreach.composeactors.data.auth.AuthenticationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideHttpClient(
        authenticationService: AuthenticationService,
    ): HttpClient {
        return HttpClient(Android) {
            install(Auth) {
                bearer {
                    loadTokens {
                        val tokens = authenticationService.getTokens().getOrNull() ?: return@loadTokens null
                        Timber.e(tokens.accessToken)
                        BearerTokens(
                            accessToken = tokens.accessToken ?: return@loadTokens null,
                            refreshToken = tokens.refreshToken,
                        )
                    }

                    refreshTokens {
                        val tokens = authenticationService.refreshSession().getOrNull() ?: return@refreshTokens null
                        BearerTokens(
                            accessToken = tokens.accessToken ?: return@refreshTokens null,
                            refreshToken = tokens.refreshToken.orEmpty(),
                        )
                    }
                }
            }

            install(Logging) {
                level = LogLevel.ALL
            }
            engine {
                connectTimeout = 10_000
                socketTimeout = 10_000
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    },
                )
                register(
                    ContentType.Text.Html,
                    KotlinxSerializationConverter(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        },
                    ),
                )
            }
        }
    }
}