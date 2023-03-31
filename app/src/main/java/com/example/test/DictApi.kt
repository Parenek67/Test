package com.example.test

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DictApi {

    @GET("/api/v1/dicservice.json/lookup")
    suspend fun getWord(
        @Query("key") key: String,
        @Query("lang") lang: String,
        @Query("text") text: String
    ): Response<YDictResponce>

}