package com.example.joao_translate.di

import com.example.joao_translate.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    @Named("baseUrl")
    fun providerBaseUrl() = "https://api.mymemory.translated.net/"

    @Singleton
    @Provides
    @Named("gson")
    fun providerGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()


    @Singleton
    @Provides
    fun getRetrofitInstance(@Named("baseUrl")baseUrl: String,@Named("gson") gson: Gson): ApiService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }

}