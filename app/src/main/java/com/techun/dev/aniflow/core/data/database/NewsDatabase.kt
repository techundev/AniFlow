package com.techun.dev.aniflow.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity
import com.techun.dev.aniflow.home.data.database.FeedDao

@Database(entities = [RssItemEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao
}