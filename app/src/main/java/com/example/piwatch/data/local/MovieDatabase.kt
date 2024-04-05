package com.example.piwatch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.piwatch.data.local.model.GenreEntity
import com.example.piwatch.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}