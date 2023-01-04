package com.newsheadlines.app.newslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.newsheadlines.app.MainCoroutineRule
import com.newsheadlines.app.data.model.Article
import com.newsheadlines.app.data.source.NewsListTestRepository
import com.newsheadlines.app.helper.NewsSourceTestHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsListViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var newsListViewModel: NewsListViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var newsListTestRepository: NewsListTestRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupNewsListViewModel() {

        // We initialise the repository with no
        newsListTestRepository = NewsListTestRepository()


        newsListViewModel = NewsListViewModel(newsListTestRepository)
    }

    @Test
    fun `check if news are loaded`(){

        val expectedResponse = NewsSourceTestHelper.readNewsList("/news_list_response.json")

        // initial state
        val emptyState = newsListViewModel.uiState.value as NewsListUIState.Empty

        // load news
        newsListViewModel.loadTopNews("")

        val newState = newsListViewModel.uiState.value

        // verify state change
        assertFalse(emptyState == newState)

        val newsFound = (newsListViewModel.uiState.value as NewsListUIState.Loaded).newsList

        assertTrue("news should have been loaded",newsListViewModel.uiState.value is NewsListUIState.Loaded)

        assert(newsFound.size == expectedResponse.articles.size)

        for (i in newsFound.indices){
            val expected = expectedResponse.articles[i]
            val actual = newsFound[i]


            assertEquals("title must be same", expected.title, actual.title)
            assertEquals("desc must be same", expected.description, actual.description)
            assertEquals("source must be same", expected.source.name, actual.source.name)
            assertEquals("author must be same", expected.author, actual.author)

        }

    }

    @Test
    fun `test articleList() given emptylist list return empty array `() {
        val givenList = emptyList<Article>()

        val result = newsListViewModel.articleList(givenList)

        assert(result.size == givenList.size)

    }


    @Test
    fun `test articleList() given null list return empty array`() {
        val givenList: List<Article>? = null

        val result = newsListViewModel.articleList(givenList)

        assert(result.isEmpty())
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `test articleList() given empty list return empty array and addressing zero index returns exception`() {
        val result = newsListViewModel.articleList(emptyList())
        val objectAtZero = result[0]

    }

    @Test(expected = NullPointerException::class)
    fun `test givenList is never null always throws exception `() {
        val givenList: List<Article>? = null
        givenList!!.size

    }

}