package com.techun.dev.aniflow.profile.di

import com.techun.dev.aniflow.profile.domain.usecase.GetProfileStatsUseCase
import com.techun.dev.aniflow.profile.domain.usecase.GetUserPreferencesUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateAvatarUrlUseCase
import com.techun.dev.aniflow.profile.domain.usecase.UpdateUserNameUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val profileDomainModule = module {
    factoryOf(::GetUserPreferencesUseCase)
    factoryOf(::UpdateUserNameUseCase)
    factoryOf(::UpdateAvatarUrlUseCase)
    factoryOf(::GetProfileStatsUseCase)
}