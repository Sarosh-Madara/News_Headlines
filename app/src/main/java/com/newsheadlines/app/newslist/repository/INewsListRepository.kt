package com.newsheadlines.app.newslist.repository

import com.newsheadlines.app.data.model.NewsListResponse

/**
 * Interface to the data layer.
 */
interface INewsListRepository {
    suspend fun topHeadlines(query: String,apiKey: String) : NewsListResponse
}