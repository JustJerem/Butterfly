package com.jeremieguillot.butterfly.di

import android.content.Context
import androidx.room.Room
import com.jeremieguillot.butterfly.data.local.AppDatabase
import com.jeremieguillot.butterfly.data.local.ButterflyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-butterfly"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideButterflyDao(database: AppDatabase): ButterflyDao {
        return database.butterflyDao()
    }
}