package com.techun.dev.aniflow.favorite.di

import com.techun.dev.aniflow.favorite.data.repository.FavoritesRepositoryImpl
import com.techun.dev.aniflow.favorite.domain.repository.FavoritesRepository
import com.techun.dev.aniflow.favorite.domain.usecase.AddFavoriteUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.GetFavoriteIdsUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.GetFavoritesUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.IsFavoriteUseCase
import com.techun.dev.aniflow.favorite.domain.usecase.RemoveFavoriteUseCase
import com.techun.dev.aniflow.favorite.ui.FavoritesViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favoritesDataModule = module {
    singleOf(::FavoritesRepositoryImpl) { bind<FavoritesRepository>() }
}

val favoritesDomainModule = module {
    factoryOf(::AddFavoriteUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::GetFavoriteIdsUseCase)
    factoryOf(::IsFavoriteUseCase)
    factoryOf(::RemoveFavoriteUseCase)
}

val favoritesUiModule = module {
    viewModelOf(::FavoritesViewModel)
}