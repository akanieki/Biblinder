package com.biblinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        AnimeEntity::class,
        AnimeListItem::class,
        CustomList::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BiblinderDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}
