package com.kurniawan.capstoneproject.bookmark.di

import com.kurniawan.capstoneproject.bookmark.bookmark.BookmarkViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bookmarkModule = module {
    viewModel { BookmarkViewModel(get()) }
}