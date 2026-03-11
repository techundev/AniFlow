package com.techun.dev.aniflow.feed.di

import com.techun.dev.aniflow.feed.data.remote.FeedRemoteDataSource
import com.techun.dev.aniflow.feed.data.repository.FeedRepositoryImpl
import com.techun.dev.aniflow.feed.domain.repository.FeedRepository
import com.techun.dev.aniflow.feed.domain.usecase.GetFeedUseCase
import com.techun.dev.aniflow.feed.domain.usecase.SyncFeedUseCase
import com.techun.dev.aniflow.feed.ui.FeedViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val feedDataModule = module {
    single {
        HttpClient(OkHttp) {
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
        }
    }
    singleOf(::FeedRemoteDataSource)
    singleOf(::FeedRepositoryImpl) { bind<FeedRepository>() }
}

val feedDomainModule = module {
    factoryOf(::GetFeedUseCase)
    factoryOf(::SyncFeedUseCase)
}

val feedUiModule = module {
    viewModelOf(::FeedViewModel)
}