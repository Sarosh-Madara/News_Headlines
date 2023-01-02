package com.newsheadlines.app.arch.di

import com.newsheadlines.app.arch.NewSourceApi
import com.newsheadlines.app.newslist.repository.INewsListRepository
import com.newsheadlines.app.newslist.repository.impl.NewsListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsSourceRepository(api: NewSourceApi) : INewsListRepository{
        return NewsListRepositoryImpl(api)
    }
}