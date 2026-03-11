package com.techun.dev.aniflow.detail.di

import com.techun.dev.aniflow.detail.data.repository.FeedDetailsRepositoryImpl
import com.techun.dev.aniflow.detail.domain.repository.FeedDetailsRepository
import com.techun.dev.aniflow.detail.domain.usecase.GetNewsItemByIdUseCase
import com.techun.dev.aniflow.detail.ui.DetailsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val detailsDataModule = module {
    singleOf(::FeedDetailsRepositoryImpl) { bind<FeedDetailsRepository>() }
}

val detailsDomainModule = module {
    factoryOf(::GetNewsItemByIdUseCase)
}

val detailsUiModule = module {
    viewModelOf(::DetailsViewModel)
}