package com.example.joao_translate.domain

import com.example.joao_translate.model.PatternLanguage
import com.example.joao_translate.network.Repository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject

class TranslateBusiness {
    fun getTranslatedText(text: String, langpair: Pair<PatternLanguage, PatternLanguage>, cb:(String)->Unit) {
        Repository.getData(
            text,
            convertPairToISO(langpair),
            onSucesso = {
                cb((it?.get("responseData") as JSONObject).get("translatedText") as String)
            },
            onError = {
                cb(it.toString())
            }
        )
    }

    private fun convertPairToISO(langpair: Pair<PatternLanguage, PatternLanguage>): String {
        return "${langpair.first.code}|${langpair.second.code}"
    }
}