package com.example.joao_translate.di

import com.example.joao_translate.domain.TranslateBusiness
import com.example.joao_translate.network.ApiService
import com.example.joao_translate.network.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
    ): Repository {
        return Repository(
            apiService
        )
    }

    @Singleton
    @Provides
    fun provideTranslateBusiness(
        repository: Repository
    ): TranslateBusiness{
        return TranslateBusiness(
            repository
        )
    }

}