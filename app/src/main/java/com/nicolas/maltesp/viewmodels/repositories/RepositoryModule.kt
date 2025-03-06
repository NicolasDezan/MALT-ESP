package com.nicolas.maltesp.viewmodels.repositories

import com.nicolas.maltesp.viewmodels.repositories.impl.ScaffoldRepositoryImpl
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
}