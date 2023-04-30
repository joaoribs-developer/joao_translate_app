package com.example.joao_translate.network

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/get")
    fun getSomeData(@Query("q") q: String, @Query("langpair") langpair: String): Call<Any?>
}