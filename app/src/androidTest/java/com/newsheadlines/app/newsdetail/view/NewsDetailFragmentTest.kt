package com.newsheadlines.app.newsdetail.view

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.newsheadlines.app.R
import com.newsheadlines.app.helper.NewsSourceTestHelper
import com.newsheadlines.app.util.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration test for the News Details screen.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class NewsDetailFragmentTest {

    @Test
    fun activeNewsDetails_DisplayedInUi() {

        // GIVEN - News detail from news source
        val articleModel = NewsSourceTestHelper.readNewsList("/news_list_response.json").articles[0]


        // WHEN - Details fragment launched to display news
        val bundle = NewsDetailFragmentArgs(articleModel).toBundle()
        launchFragmentInHiltContainer<NewsDetailFragment>(fragmentArgs = bundle) {
            Thread.sleep(3000)
        }

        // THEN - News details are displayed on the screen
        // make sure that the title/description/author/ are both shown and correct
        Espresso.onView(withId(R.id.txtNewsSource))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.txtTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.imgNewsItem))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.txtContent))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.txtAuthor))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.txtNewsSource))
            .check(ViewAssertions.matches(ViewMatchers.withText(articleModel.source.name)))
        Espresso.onView(withId(R.id.txtTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText(articleModel.title)))
        Espresso.onView(withId(R.id.txtContent))
            .check(ViewAssertions.matches(ViewMatchers.withText(articleModel.content)))
        Espresso.onView(withId(R.id.txtAuthor))
            .check(ViewAssertions.matches(ViewMatchers.withText(articleModel.author)))
    }

    @Test
    fun testSwipeUp_thenSwipeDown() {
        // GIVEN - News detail from news source
        val articleModel = NewsSourceTestHelper.readNewsList("/news_list_response.json").articles[0]


        // WHEN - Details fragment launched to display news
        val bundle = NewsDetailFragmentArgs(articleModel).toBundle()

        launchFragmentInHiltContainer<NewsDetailFragment>(fragmentArgs = bundle) {
            Thread.sleep(3000)
        }

        // swipe up and down
        Espresso.onView(withId(R.id.scrollView)).perform(ViewActions.swipeUp())
        Espresso.onView(withId(R.id.scrollView)).perform(ViewActions.swipeDown())

    }

}