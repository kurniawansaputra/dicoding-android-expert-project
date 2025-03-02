package com.kurniawan.capstoneproject.core.utils

import com.kurniawan.capstoneproject.core.data.source.local.entity.NewsEntity
import com.kurniawan.capstoneproject.core.data.source.remote.response.NewsResponse
import com.kurniawan.capstoneproject.core.domain.model.News

object DataMapper {
    fun mapResponsesToEntities(input: List<NewsResponse>): List<NewsEntity> {
        val newsList = ArrayList<NewsEntity>()
        input.map {
            val news = NewsEntity(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isBookmark = false
            )
            newsList.add(news)
        }
        return newsList
    }

    fun mapEntitiesToDomain(input: List<NewsEntity>): List<News> =
       input.map {
            News(
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content,
                isBookmark = it.isBookmark
            )
       }

    fun mapDomainToEntity(input: News) = NewsEntity(
        author = input.author,
        title = input.title,
        description = input.description,
        url = input.url,
        urlToImage = input.urlToImage,
        publishedAt = input.publishedAt,
        content = input.content,
        isBookmark = input.isBookmark
    )
}