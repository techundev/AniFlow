package com.techun.dev.aniflow

import android.app.Application
import com.techun.dev.aniflow.core.di.roomModule
import com.techun.dev.aniflow.detail.di.detailsDataModule
import com.techun.dev.aniflow.detail.di.detailsDomainModule
import com.techun.dev.aniflow.detail.di.detailsUiModule
import com.techun.dev.aniflow.favorite.di.favoritesDataModule
import com.techun.dev.aniflow.favorite.di.favoritesDomainModule
import com.techun.dev.aniflow.favorite.di.favoritesUiModule
import com.techun.dev.aniflow.feed.di.feedDataModule
import com.techun.dev.aniflow.feed.di.feedDomainModule
import com.techun.dev.aniflow.feed.di.feedUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AniFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AniFlowApp)
            modules(
                roomModule,
                feedDataModule,
                feedDomainModule,
                feedUiModule,
                favoritesDataModule,
                favoritesDomainModule,
                favoritesUiModule,
                detailsDataModule,
                detailsDomainModule,
                detailsUiModule
            )
        }
    }
}