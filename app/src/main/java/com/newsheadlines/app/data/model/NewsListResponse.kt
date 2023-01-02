package com.newsheadlines.app.data.model


data class NewsListResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
    val code: String,
    val message: String
)