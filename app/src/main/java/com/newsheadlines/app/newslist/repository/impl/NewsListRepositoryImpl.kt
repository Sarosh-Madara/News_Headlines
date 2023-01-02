package com.newsheadlines.app.newslist.repository.impl

import com.newsheadlines.app.arch.NewSourceApi
import com.newsheadlines.app.data.model.NewsListResponse
import com.newsheadlines.app.newslist.repository.INewsListRepository
import javax.inject.Inject

class NewsListRepositoryImpl @Inject constructor(private val api: NewSourceApi) : INewsListRepository {

    override suspend fun topHeadlines(query: String,apiKey: String): NewsListResponse {
        return api.topHeadlines(query,apiKey)
    }
}