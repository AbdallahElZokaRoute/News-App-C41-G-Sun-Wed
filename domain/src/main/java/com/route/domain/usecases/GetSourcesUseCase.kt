package com.route.domain.usecases

import com.route.domain.entities.SourcesItemEntity
import com.route.domain.repositories.NewsRepository
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(categoryId: String): List<SourcesItemEntity> {
        return repository.getSourcesByCategory(categoryId)
    }
}