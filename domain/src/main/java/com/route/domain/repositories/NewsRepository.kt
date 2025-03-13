package com.route.domain.repositories

import com.route.domain.entities.ArticlesItemEntity
import com.route.domain.entities.SourcesItemEntity


interface NewsRepository {
    suspend fun getSourcesByCategory(categoryId: String): List<SourcesItemEntity>
    suspend fun getNewsBySource(sourceId: String): List<ArticlesItemEntity>
}

interface NewsOnlineDataSource {
    suspend fun fetchSourcesByCategory(categoryId: String): List<SourcesItemEntity>
    suspend fun fetchNewsBySource(sourceId: String): List<ArticlesItemEntity>
}
