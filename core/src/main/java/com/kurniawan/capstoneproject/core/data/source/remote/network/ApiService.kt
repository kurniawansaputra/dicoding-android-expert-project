package com.kurniawan.capstoneproject.core.data.source.remote.network

import com.kurniawan.capstoneproject.core.data.source.remote.response.ListNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getList(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): ListNewsResponse
}