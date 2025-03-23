package com.nicolas.maltesp.data.di

import android.content.Context
import com.nicolas.maltesp.data.local.dao.MaltingRecipeDao
import com.nicolas.maltesp.data.repositories.BluetoothRepositoryImpl
import com.nicolas.maltesp.data.repositories.ParametersRepositoryImpl
import com.nicolas.maltesp.data.repositories.RecipesRepositoryImpl
import com.nicolas.maltesp.data.repositories.ScaffoldRepositoryImpl
import com.nicolas.maltesp.data.repositories.SettingsRepositoryImpl
import com.nicolas.maltesp.data.repositories.SharedRepositoryImpl
import com.nicolas.maltesp.domain.repositories.BluetoothRepository
import com.nicolas.maltesp.domain.repositories.ParametersRepository
import com.nicolas.maltesp.domain.repositories.RecipesRepository
import com.nicolas.maltesp.domain.repositories.ScaffoldRepository
import com.nicolas.maltesp.domain.repositories.SettingsRepository
import com.nicolas.maltesp.domain.repositories.SharedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideScaffoldRepository(
    ): ScaffoldRepository {
        return ScaffoldRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSharedRepository(): SharedRepository {
        return SharedRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRecipesRepository(
        dao: MaltingRecipeDao,
        sharedRepository: SharedRepository,
    ): RecipesRepository {
        return RecipesRepositoryImpl(dao, sharedRepository)
    }

    @Provides
    @Singleton
    fun provideParametersRepository(
        sharedRepository: SharedRepository
    ): ParametersRepository {
        return ParametersRepositoryImpl(sharedRepository)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideBluetoothRepository(
        @ApplicationContext context: Context,
        sharedRepository: SharedRepository
    ): BluetoothRepository {
        return BluetoothRepositoryImpl(context, sharedRepository)
    }
}