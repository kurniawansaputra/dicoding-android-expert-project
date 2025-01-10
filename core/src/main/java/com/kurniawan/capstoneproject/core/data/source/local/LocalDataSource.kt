package com.kurniawan.capstoneproject.core.data.source.local

import com.kurniawan.capstoneproject.core.data.source.local.entity.NewsEntity
import com.kurniawan.capstoneproject.core.data.source.local.room.NewsDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val newsDao: NewsDao) {
    fun getAllNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()

    fun getBookmarkNews(): Flow<List<NewsEntity>> = newsDao.getBookmarkNews()

    suspend fun insertNews(newsList: List<NewsEntity>) = newsDao.insertNews(newsList)

    fun setBookmarkNews(news: NewsEntity, newState: Boolean) {
        news.isBookmark = newState
        newsDao.updateBookmarkNews(news)
    }
}