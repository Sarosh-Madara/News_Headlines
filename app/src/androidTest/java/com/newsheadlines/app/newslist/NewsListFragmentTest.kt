package com.newsheadlines.app.newslist

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
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
import org.mockito.Mockito


/**
 * Integration test for the News List screen.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class NewsListFragmentTest{

    @Test
    fun isFragmentDisplayed(){
        launchFragmentInHiltContainer<NewsListFragment> {
            Thread.sleep(3000)
        }
        Espresso.onView(withId(R.id.fragment_news_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testSwipeUp(){
        launchFragmentInHiltContainer<NewsListFragment> {
           Thread.sleep(3000)
        }
        Espresso.onView(withId(R.id.listNews)).perform(ViewActions.swipeUp())

    }

    @Test
    fun testSwipeUp_thenSwipeDown(){

        launchFragmentInHiltContainer<NewsListFragment> {
           Thread.sleep(3000)
        }
        Espresso.onView(withId(R.id.listNews)).perform(ViewActions.swipeUp())
        Espresso.onView(withId(R.id.listNews)).perform(ViewActions.swipeDown())

    }

    @Test
    fun clickTask_navigateToDetailFragmentOne() {

        // GIVEN - News detail from news source
        val articleModel = NewsSourceTestHelper.readNewsList("/news_list_response.json").articles[0]

        val scenario = launchFragmentInContainer<NewsListFragment>(Bundle(), R.style.Theme_NewsHeadlines)
        val navController = Mockito.mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Click on the first list item
        Espresso.onView(withId(R.id.listNews))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(articleModel.title)), ViewActions.click()
                ))


        // THEN - Verify that we navigate to the first detail screen
        Mockito.verify(navController).navigate(
            NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(articleModel = articleModel)
        )
    }


    @Test
    fun matchItemCount() {
        var itemCount :Int? = 0
        launchFragmentInHiltContainer<NewsListFragment> {
            Thread.sleep(2000)
            itemCount = this.binding.listNews.adapter?.itemCount
        }
        Espresso.onView(withId(R.id.listNews))
            .perform(itemCount?.let { count ->
                RecyclerViewActions.scrollToPosition<NewsListAdapter.ViewHolder>(count-1)
            })
    }

}