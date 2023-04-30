package com.example.joao_translate.network

import com.example.joao_translate.network.ConvertFactory.linkedTreeMapToJsonObject
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val retrofit: ApiService) {
    @Suppress("UNCHECKED_CAST")
    fun getData(
        text: String,
        langpair: String,
        onSucesso: (JSONObject?) -> Unit,
        onError: (Any?) -> Unit
    ) {
        retrofit.getSomeData(text, langpair).enqueue(object : Callback<Any?> {

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                onError(t)
            }

            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                onSucesso(linkedTreeMapToJsonObject((response.body() as LinkedTreeMap<Any, Any>)))
            }
        })
    }
}

object ConvertFactory {
    fun linkedTreeMapToJsonObject(data: LinkedTreeMap<Any, Any>): JSONObject {
        val json = Gson().toJson(data)
        return JSONObject(json)
    }
}