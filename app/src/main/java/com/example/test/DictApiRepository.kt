package com.example.test

import retrofit2.Response

class DictApiRepository {

    suspend fun getWord(key: String, lang: String, text: String): Response<YDictResponce> {
        return RetrofitInstance.api.getWord(key, lang, text)
    }
}