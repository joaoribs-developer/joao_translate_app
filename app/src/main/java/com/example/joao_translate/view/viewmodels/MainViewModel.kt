package com.example.joao_translate.view.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.joao_translate.domain.TranslateBusiness
import com.example.joao_translate.model.PatternLanguage

class MainViewModel : ViewModel() {
    private val translateBusiness = TranslateBusiness()
    var isLoading by mutableStateOf(false)
    var firstField by mutableStateOf(PatternLanguage.INGLES)
    var secondField by mutableStateOf(PatternLanguage.PORTUGUÊS)
    var textForTranslate by mutableStateOf("")
    var textTranslated by mutableStateOf("")

    fun getTextTranslated() {
        isLoading = true
        if (textForTranslate.isNotBlank() && textForTranslate.isNotEmpty()){
           translateBusiness.getTranslatedText(
                textForTranslate,
                Pair(firstField, secondField)
            ){
                textTranslated = it
                isLoading = false
            }
        }
    }
    fun invertFields(){
        val lastFirstField = firstField
        firstField = secondField
        secondField = lastFirstField
    }
}