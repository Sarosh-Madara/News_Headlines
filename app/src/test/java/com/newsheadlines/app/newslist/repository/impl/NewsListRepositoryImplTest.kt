package com.newsheadlines.app.newslist.repository.impl

import com.newsheadlines.app.data.model.NewsListResponse
import com.newsheadlines.app.helper.NewsSourceTestHelper
import com.newsheadlines.app.newslist.repository.INewsListRepository

class NewsListRepositoryImplTest : INewsListRepository{


    private val newslistReposne = NewsSourceTestHelper.readNewsList("/news_list_response.json")



    override suspend fun topHeadlines(query: String, apiKey: String): NewsListResponse {
        return newslistReposne
    }
}