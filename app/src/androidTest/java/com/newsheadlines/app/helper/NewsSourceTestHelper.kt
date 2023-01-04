package com.newsheadlines.app.helper

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.newsheadlines.app.data.model.NewsListResponse
import java.io.FileReader
import java.nio.file.Paths

class NewsSourceTestHelper {

    companion object{
        private val gson = GsonBuilder().create()

        private val newsListType = object : TypeToken<NewsListResponse>(){}.type

        fun readNewsList(fileName: String) : NewsListResponse{
            return gson.fromJson<NewsListResponse>(
                FileReader(Paths.get(NewsSourceTestHelper::class.java.getResource(fileName)!!.toURI()).toFile()),
                newsListType
            )
        }

    }
}