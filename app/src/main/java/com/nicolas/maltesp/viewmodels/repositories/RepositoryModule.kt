package com.nicolas.maltesp.viewmodels.repositories

import android.content.Context
import com.nicolas.maltesp.data.MaltingRecipeDao
import com.nicolas.maltesp.viewmodels.repositories.impl.BluetoothRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.ParametersRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.RecipesRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.ScaffoldRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.SettingsRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.SharedRepositoryImpl
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
        @ApplicationContext context: Context
    ): BluetoothRepository {
        return BluetoothRepositoryImpl(context)
    }
}