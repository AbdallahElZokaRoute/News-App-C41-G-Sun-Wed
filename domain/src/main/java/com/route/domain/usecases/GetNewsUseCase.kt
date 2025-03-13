package com.route.domain.usecases

import com.route.domain.entities.ArticlesItemEntity
import com.route.domain.repositories.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
) {
    suspend fun invoke(sourceId: String): List<ArticlesItemEntity> {
        return repository.getNewsBySource(sourceId)
    }
}
