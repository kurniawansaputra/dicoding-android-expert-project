package com.kurniawan.capstoneproject.core.data.source.remote

import android.util.Log
import com.kurniawan.capstoneproject.core.BuildConfig
import com.kurniawan.capstoneproject.core.data.source.remote.network.ApiResponse
import com.kurniawan.capstoneproject.core.data.source.remote.network.ApiService
import com.kurniawan.capstoneproject.core.data.source.remote.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllNews(): Flow<ApiResponse<List<NewsResponse>>> {
        return flow {
            try {
                val response = apiService.getList(
                    "techcrunch",
                    BuildConfig.API_KEY
                )
                val dataArray = response.articles
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

