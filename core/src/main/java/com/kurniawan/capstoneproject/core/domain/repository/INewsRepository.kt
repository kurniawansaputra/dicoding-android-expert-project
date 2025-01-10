package com.kurniawan.capstoneproject.core.domain.repository

import com.kurniawan.capstoneproject.core.data.Resource
import com.kurniawan.capstoneproject.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun getAllNews(): Flow<Resource<List<News>>>
    fun getBookmarkNews(): Flow<List<News>>
    fun setBookmarkNews(news: News, state: Boolean)
}