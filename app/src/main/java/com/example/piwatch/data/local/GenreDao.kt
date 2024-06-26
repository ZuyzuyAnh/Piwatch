package com.example.piwatch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.piwatch.data.local.model.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genres: List<GenreEntity>)

    @Query("SELECT * FROM genre")
    fun getAllGenre(): List<GenreEntity>

    @Query("SELECT name FROM genre WHERE id = :genreId")
    fun getGenre(genreId: Int): String
}