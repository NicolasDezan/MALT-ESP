package com.nicolas.maltesp.viewmodels.repositories

import android.content.Context
import androidx.room.Room
import com.nicolas.maltesp.data.AppDatabase
import com.nicolas.maltesp.data.MaltingRecipeDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideMaltingRecipeDao(database: AppDatabase): MaltingRecipeDao {
        return database.maltingRecipeDao()
    }
}