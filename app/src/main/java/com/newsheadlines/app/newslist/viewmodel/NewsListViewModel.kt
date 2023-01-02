package com.newsheadlines.app.newslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsheadlines.app.BuildConfig
import com.newsheadlines.app.data.model.Article
import com.newsheadlines.app.newslist.repository.INewsListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsListViewModel @Inject constructor(val newsListRepository: INewsListRepository) : ViewModel(){


    private val _uiState = MutableStateFlow<NewsListUIState>(NewsListUIState.Empty)
    val uiState : StateFlow<NewsListUIState> = _uiState.asStateFlow()

    fun loadTopNews(source: String) = viewModelScope.launch {
        if(_uiState.value !is NewsListUIState.Loaded){
            _uiState.value = NewsListUIState.Loading
        }

        val news = newsListRepository.topHeadlines(source,BuildConfig.NEWS_API_KEY)
        if(news.articles.isEmpty()){
            _uiState.value = NewsListUIState.NoNews
        }else {
            _uiState.value = NewsListUIState.Loaded(news.articles)
        }
    }

}


sealed class NewsListUIState{
    object Empty : NewsListUIState()
    object Loading : NewsListUIState()
    object NoNews : NewsListUIState()
    data class Loaded(val newsList : List<Article>) : NewsListUIState()
}