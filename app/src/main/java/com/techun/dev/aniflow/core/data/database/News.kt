package com.techun.dev.aniflow.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techun.dev.aniflow.core.data.database.entity.RssItemEntity

@Database(entities = [RssItemEntity::class], version = 1)
abstract class News : RoomDatabase() {

}