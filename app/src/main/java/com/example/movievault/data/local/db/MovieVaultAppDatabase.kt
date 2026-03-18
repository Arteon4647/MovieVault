package com.example.movievault.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movievault.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieVaultAppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}