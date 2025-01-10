package com.kurniawan.capstoneproject.core.data

import com.kurniawan.capstoneproject.core.data.source.local.LocalDataSource
import com.kurniawan.capstoneproject.core.data.source.remote.RemoteDataSource
import com.kurniawan.capstoneproject.core.data.source.remote.network.ApiResponse
import com.kurniawan.capstoneproject.core.data.source.remote.response.NewsResponse
import com.kurniawan.capstoneproject.core.domain.model.News
import com.kurniawan.capstoneproject.core.domain.repository.INewsRepository
import com.kurniawan.capstoneproject.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): INewsRepository {

    override fun getAllNews(): Flow<Resource<List<News>>> =
        object : NetworkBoundResource<List<News>, List<NewsResponse>>() {
            override fun loadFromDB(): Flow<List<News>> {
                return localDataSource.getAllNews().map {
                    com.kurniawan.capstoneproject.core.utils.DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<News>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<NewsResponse>>> =
                remoteDataSource.getAllNews()

            override suspend fun saveCallResult(data: List<NewsResponse>) {
                val newsList = com.kurniawan.capstoneproject.core.utils.DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNews(newsList)
            }
        }.asFlow()

    override fun getBookmarkNews(): Flow<List<News>> {
        return localDataSource.getBookmarkNews().map {
            com.kurniawan.capstoneproject.core.utils.DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setBookmarkNews(news: News, state: Boolean) {
        val newsEntity = com.kurniawan.capstoneproject.core.utils.DataMapper.mapDomainToEntity(news)
        appExecutors.diskIO().execute { localDataSource.setBookmarkNews(newsEntity, state) }
    }
}
