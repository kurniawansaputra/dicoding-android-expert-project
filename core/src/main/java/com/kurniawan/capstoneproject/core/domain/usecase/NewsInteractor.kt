package com.kurniawan.capstoneproject.core.domain.usecase

import com.kurniawan.capstoneproject.core.data.Resource
import com.kurniawan.capstoneproject.core.domain.model.News
import com.kurniawan.capstoneproject.core.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class NewsInteractor(private val newsRepository: INewsRepository): NewsUseCase {
    override fun getAllNews(): Flow<Resource<List<News>>> {
        return newsRepository.getAllNews()
    }

    override fun getBookmarkNews(): Flow<List<News>> {
        return newsRepository.getBookmarkNews()
    }

    override fun setBookmarkNews(news: News, state: Boolean) {
        return newsRepository.setBookmarkNews(news, state)
    }
}