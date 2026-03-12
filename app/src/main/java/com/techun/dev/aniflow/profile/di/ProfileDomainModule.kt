package com.techun.dev.aniflow.profile.di

import com.techun.dev.aniflow.profile.data.datastore.UserPreferencesDataStore
import com.techun.dev.aniflow.profile.data.repository.ProfileRepositoryImpl
import com.techun.dev.aniflow.profile.domain.repository.ProfileRepository
import com.techun.dev.aniflow.profile.domain.usecase.GetProfileStatsUseCase
import com.techun.dev.aniflow.profile.domain.usecase.GetUserPreferencesUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateAvatarUrlUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateUserNameUseCase
import com.techun.dev.aniflow.profile.ui.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileDataModule = module {
    single { UserPreferencesDataStore(androidContext()) }
    singleOf(::ProfileRepositoryImpl) { bind<ProfileRepository>() }
}
val profileDomainModule = module {
    factoryOf(::GetUserPreferencesUseCase)
    factoryOf(::UpdateUserNameUseCase)
    factoryOf(::UpdateAvatarUrlUseCase)
    factoryOf(::GetProfileStatsUseCase)
}

val profileUiModule = module {
    viewModelOf(::ProfileViewModel)
}