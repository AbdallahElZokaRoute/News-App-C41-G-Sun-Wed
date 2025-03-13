package com.route.data.di

import com.route.data.api.NewsServices
import com.route.data.dataSource.online.NewsOnlineDataSourceImpl
import com.route.data.repositories.NewsRepositoryImpl
import com.route.domain.repositories.NewsOnlineDataSource
import com.route.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideOnlineDataSource(
        newsServices: NewsServices
    ): NewsOnlineDataSource {
        return NewsOnlineDataSourceImpl(newsServices)
    }

    @Singleton
    @Provides
    fun provideNewsRepository(
        onlineDataSource: NewsOnlineDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(onlineDataSource)
    }

}
