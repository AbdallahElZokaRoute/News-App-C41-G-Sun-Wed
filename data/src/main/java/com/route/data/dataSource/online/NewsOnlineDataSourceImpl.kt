package com.route.data.dataSource.online

import com.route.data.api.NewsServices
import com.route.data.mappers.toEntity
import com.route.domain.entities.ArticlesItemEntity
import com.route.domain.entities.SourcesItemEntity
import com.route.domain.repositories.NewsOnlineDataSource

class NewsOnlineDataSourceImpl(
    private val newsServices: NewsServices
) : NewsOnlineDataSource {
    override suspend fun fetchSourcesByCategory(categoryId: String): List<SourcesItemEntity> {
        return newsServices.getSources(categoryId).body()?.toEntity()?.sources ?: emptyList()
    }

    override suspend fun fetchNewsBySource(sourceId: String): List<ArticlesItemEntity> {
        return newsServices.getNewsBySource(sourceId).body()?.toEntity()?.articles ?: emptyList()
    }

}
