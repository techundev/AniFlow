package com.techun.dev.aniflow.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import com.techun.dev.aniflow.detail.data.local.dao.DetailsDao
import com.techun.dev.aniflow.favorite.data.local.dao.FavoritesDao
import com.techun.dev.aniflow.favorite.data.local.entity.FavoriteEntity
import com.techun.dev.aniflow.feed.data.database.FeedDao
import com.techun.dev.aniflow.profile.data.local.dao.ProfileDao

@Database(entities = [RssItemEntity::class, FavoriteEntity::class], version = 3)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun detailsDao(): DetailsDao

    abstract fun profileDao(): ProfileDao
}