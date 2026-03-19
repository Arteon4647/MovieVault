package com.example.movievault.di.db

import android.content.Context
import androidx.room.Room
import com.example.movievault.data.local.db.FavoriteMovieDao
import com.example.movievault.data.local.db.MovieVaultAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "movievault_db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieVaultAppDatabase {
        return Room.databaseBuilder(
            context,
            MovieVaultAppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration(false).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieDao(database: MovieVaultAppDatabase): FavoriteMovieDao {
        return database.favoriteMovieDao()
    }
}