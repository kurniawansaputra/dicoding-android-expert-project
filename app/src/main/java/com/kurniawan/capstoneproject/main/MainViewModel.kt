package com.kurniawan.capstoneproject.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kurniawan.capstoneproject.core.domain.usecase.NewsUseCase

class MainViewModel(newsUseCase: NewsUseCase): ViewModel() {
    val news = newsUseCase.getAllNews().asLiveData()
}