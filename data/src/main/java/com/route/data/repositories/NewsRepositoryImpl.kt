package com.route.data.repositories

import com.route.domain.entities.ArticlesItemEntity
import com.route.domain.entities.SourcesItemEntity
import com.route.domain.repositories.NewsOnlineDataSource
import com.route.domain.repositories.NewsRepository

class NewsRepositoryImpl(
    private val onlineDataSource: NewsOnlineDataSource,
) : NewsRepository {
    override suspend fun getSourcesByCategory(categoryId: String): List<SourcesItemEntity> {
        return onlineDataSource.fetchSourcesByCategory(categoryId)
    }

    override suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity> {
        return onlineDataSource.fetchNewsBySource(sourceId)
    }

}
