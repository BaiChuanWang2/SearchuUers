package com.example.searchusers.data.api

import com.example.searchusers.data.api.`in`.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    suspend fun search(@Query("q") q: String, @Query("per_page") per_page: Int = 20, @Query("page") page: Int): SearchResponse
}