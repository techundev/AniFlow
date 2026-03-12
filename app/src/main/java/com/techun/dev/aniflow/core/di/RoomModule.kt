package com.techun.dev.aniflow.core.di

import androidx.room.Room
import com.techun.dev.aniflow.core.data.database.NewsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val WIKI_DATABASE = "news_database"

val roomModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(), klass = NewsDatabase::class.java, name = WIKI_DATABASE
        ).fallbackToDestructiveMigration(false).build()
    }

    single { get<NewsDatabase>().feedDao() }
    single { get<NewsDatabase>().favoritesDao() }
    single { get<NewsDatabase>().detailsDao() }
    single { get<NewsDatabase>().profileDao() }
}