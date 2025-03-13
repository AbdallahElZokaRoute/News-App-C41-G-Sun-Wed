package com.route.domain.entities

data class SourcesResponseEntity(
    val sources: List<SourcesItemEntity>? = null,
    val status: String? = null,
    val code: String? = null,
    val message: String? = null
)

data class SourcesItemEntity(
    val name: String? = null,
    val id: String? = null,
)
