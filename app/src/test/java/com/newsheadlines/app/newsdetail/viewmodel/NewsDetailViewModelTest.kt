package com.newsheadlines.app.newsdetail.viewmodel

import com.newsheadlines.app.helper.NewsSourceTestHelper
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NewsDetailViewModelTest {

    // subject under test
    private lateinit var viewModel: NewsDetailViewModel

    @Before
    fun setupViewModel() {
        viewModel = NewsDetailViewModel()
    }

    @Test
    fun `test initModel() given News model and verify state`() {

        val response = NewsSourceTestHelper.readNewsList("/news_list_response.json")

        // initial state
        val emptyState = viewModel.uiState.value

        // when given
        viewModel.initNewsModel(response.articles[0])

        val newState = viewModel.uiState.value  as NewsDetailUIState.Loaded

        // verify state
        assertFalse(newState == emptyState)

        assertTrue(
            "news should have been loaded",
            viewModel.uiState.value is NewsDetailUIState.Loaded
        )

        assert(emptyState is NewsDetailUIState.Empty)

        MatcherAssert.assertThat(emptyState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(newState, CoreMatchers.notNullValue())

        MatcherAssert.assertThat(emptyState, CoreMatchers.instanceOf(NewsDetailUIState.Empty::class.java))
        MatcherAssert.assertThat(newState, CoreMatchers.instanceOf(NewsDetailUIState.Loaded::class.java))

    }
    @Test
    fun `test initModel() given News model and verify data`() {

        val response = NewsSourceTestHelper.readNewsList("/news_list_response.json")

        // initial state
        val emptyState = viewModel.uiState.value

        // when given
        viewModel.initNewsModel(response.articles[0])

        val newState = viewModel.uiState.value  as NewsDetailUIState.Loaded

        // verify state
        assertFalse(newState == emptyState)

        assertTrue(
            "news should have been loaded",
            viewModel.uiState.value is NewsDetailUIState.Loaded
        )

        val actual = response.articles[0]

        assert(emptyState is NewsDetailUIState.Empty)

        MatcherAssert.assertThat(emptyState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(newState, CoreMatchers.notNullValue())

        assertEquals("title must be same", newState.article.title, actual.title)
        assertEquals("desc must be same", newState.article.description, actual.description)
        assertEquals("source must be same", newState.article.source.name, actual.source.name)
        assertEquals("author must be same", newState.article.author, actual.author)

    }

}