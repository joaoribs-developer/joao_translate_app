package com.example.joao_translate.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object{
        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        const val baseUrl = "https://api.mymemory.translated.net/"
        fun getRetrofitInstance(): ApiService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}