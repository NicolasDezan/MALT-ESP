package com.nicolas.maltesp.viewmodels.repositories

import com.nicolas.maltesp.data.MaltingRecipeDao
import com.nicolas.maltesp.viewmodels.repositories.impl.RecipesRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.ScaffoldRepositoryImpl
import com.nicolas.maltesp.viewmodels.repositories.impl.SharedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}