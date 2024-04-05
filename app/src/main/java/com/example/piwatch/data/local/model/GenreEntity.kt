package com.example.piwatch.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.piwatch.domain.model.Genre

@Entity(tableName = "genre")
data class GenreEntity(
    @PrimaryKey
    val id: Int,
    val name: String
)

fun GenreEntity.toGenre() = Genre(
    id, name
)
