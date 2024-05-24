package com.example.piwatch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.piwatch.data.local.model.MovieEntity


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movie")
    fun deleteAllMovies(): Int

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<MovieEntity>
}