package com.route.data.mappers

import com.route.data.api.model.SourcesItem
import com.route.data.api.model.SourcesResponse
import com.route.domain.entities.SourcesItemEntity
import com.route.domain.entities.SourcesResponseEntity

fun SourcesResponse.toEntity(): SourcesResponseEntity {
    return SourcesResponseEntity(sources?.map {
        it.toEntity()
    }, status, code, message)
}

fun SourcesItem.toEntity(): SourcesItemEntity {
    return SourcesItemEntity(name, id)
}
