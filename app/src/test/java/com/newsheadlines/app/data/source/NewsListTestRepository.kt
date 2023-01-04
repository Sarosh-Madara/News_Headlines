package com.newsheadlines.app.data.source

import com.newsheadlines.app.MainCoroutineRule
import com.newsheadlines.app.arch.NewSourceApi
import com.newsheadlines.app.data.model.NewsListResponse
import com.newsheadlines.app.helper.NewsSourceTestHelper
import com.newsheadlines.app.newslist.repository.INewsListRepository
import com.newsheadlines.app.newslist.repository.impl.NewsListRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class NewsListTestRepository : INewsListRepository {


    // subject under test
    private lateinit var newsListRepositoryImpl: NewsListRepositoryImpl

    val newslistReponse = NewsSourceTestHelper.readNewsList("/news_list_response.json")
    val newslistReponseBbc = NewsSourceTestHelper.readNewsList("/news_list_bbc_response.json")

    @MockK
    private lateinit var apiService : NewSourceApi


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        newsListRepositoryImpl = NewsListRepositoryImpl(apiService)

    }

    @Test
    fun `test topHeadlines() given techcrunch source`() = runTest {

        // When
        coEvery { newsListRepositoryImpl.topHeadlines("techcrunch",any()) }
            .returns(newslistReponse)

        // invoke
        val apiResponse = newsListRepositoryImpl.topHeadlines("techcrunch","")

        // Then
        MatcherAssert.assertThat(apiResponse,CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponse,not(CoreMatchers.nullValue()))

        MatcherAssert.assertThat(apiResponse, CoreMatchers.instanceOf(NewsListResponse::class.java))

        MatcherAssert.assertThat(newslistReponse.articles.size,`is`(apiResponse.articles.size))

        coVerify(exactly = 1){apiService.topHeadlines("techcrunch","")}
        confirmVerified(apiService)



    }

    @Test
    fun `test topHeadlines() given bbc source`() = runTest {

        // When
        coEvery { newsListRepositoryImpl.topHeadlines("bbc",any()) }
            .returns(newslistReponseBbc)

        // invoke
        val apiResponse = newsListRepositoryImpl.topHeadlines("bbc","")

        // Then
        MatcherAssert.assertThat(apiResponse,CoreMatchers.notNullValue())
        MatcherAssert.assertThat(apiResponse,not(CoreMatchers.nullValue()))

        MatcherAssert.assertThat(apiResponse, CoreMatchers.instanceOf(NewsListResponse::class.java))

        MatcherAssert.assertThat(newslistReponseBbc.articles.size,`is`(apiResponse.articles.size))
        MatcherAssert.assertThat(newslistReponseBbc.articles[0].source.name,`is`(apiResponse.articles[0].source.name))

        coVerify(exactly = 1){apiService.topHeadlines("bbc","")}
        confirmVerified(apiService)



    }

    override suspend fun topHeadlines(query: String, apiKey: String): NewsListResponse {
        return NewsSourceTestHelper.readNewsList("/news_list_response.json")
    }


}