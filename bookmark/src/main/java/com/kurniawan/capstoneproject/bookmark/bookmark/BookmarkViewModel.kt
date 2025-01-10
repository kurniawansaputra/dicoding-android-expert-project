package com.kurniawan.capstoneproject.bookmark.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kurniawan.capstoneproject.core.domain.usecase.NewsUseCase

class BookmarkViewModel(newsUseCase: NewsUseCase): ViewModel() {
    val bookmarkNews = newsUseCase.getBookmarkNews().asLiveData()
}