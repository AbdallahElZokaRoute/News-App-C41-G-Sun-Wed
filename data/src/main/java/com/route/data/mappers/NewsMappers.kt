package com.route.data.mappers

import com.route.data.api.model.ArticlesItem
import com.route.data.api.model.NewsResponse
import com.route.domain.entities.ArticlesItemEntity
import com.route.domain.entities.NewsResponseEntity

fun NewsResponse.toEntity(): NewsResponseEntity {
    return NewsResponseEntity(totalResults, articles?.map {
        it.toEntity()
    }, status, code, message)
}

fun ArticlesItem.toEntity(): ArticlesItemEntity {
    return ArticlesItemEntity(publishedAt, author, urlToImage, description, title, url, content)
}
