package com.kurniawan.capstoneproject.detail

import androidx.lifecycle.ViewModel
import com.kurniawan.capstoneproject.core.domain.model.News
import com.kurniawan.capstoneproject.core.domain.usecase.NewsUseCase

class DetailNewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {
    fun setFavoriteNews(news: News, newStatus: Boolean) {
        newsUseCase.setBookmarkNews(news, newStatus)
    }
}