package com.kurniawan.capstoneproject.di

import com.kurniawan.capstoneproject.core.domain.usecase.NewsInteractor
import com.kurniawan.capstoneproject.core.domain.usecase.NewsUseCase
import com.kurniawan.capstoneproject.detail.DetailNewsViewModel
import com.kurniawan.capstoneproject.main.MainViewModel
import org.koin.core.module.dsl.viewModel

import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailNewsViewModel(get()) }
}