package com.kurniawan.capstoneproject.core.domain.usecase

import com.kurniawan.capstoneproject.core.data.Resource
import com.kurniawan.capstoneproject.core.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {
    fun getAllNews(): Flow<Resource<List<News>>>
    fun getBookmarkNews(): Flow<List<News>>
    fun setBookmarkNews(news: News, state: Boolean)
}