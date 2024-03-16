package com.example.a7minutesworkout.di

import android.content.Context
import androidx.room.Room
import com.example.a7minutesworkout.data.history.HistoryDao
import com.example.a7minutesworkout.data.history.HistoryDatabase
import com.example.a7minutesworkout.data.history.HistoryRepository
import com.example.a7minutesworkout.data.history.HistoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideHistoryDao(database: HistoryDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return Room.databaseBuilder(
            context,
            HistoryDatabase::class.java,
            "history_database",
        ).fallbackToDestructiveMigration()
            .build()
    }
}

@InstallIn(ViewModelComponent::class)
@Module
abstract class HistoryModule {
    @Binds
    abstract fun providesHistoryRepository(impl: HistoryRepositoryImpl): HistoryRepository
}