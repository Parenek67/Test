package com.example.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class DictApiViewModel(val rep: DictApiRepository): ViewModel() {

    val response: MutableLiveData<Response<YDictResponce>> = MutableLiveData()

    fun getWord(key: String, lang: String, text: String){
        viewModelScope.launch {
            response.value = rep.getWord(key, lang, text)
        }
    }
}