package com.newsheadlines.app.newsdetail.viewmodel

import androidx.lifecycle.ViewModel
import com.newsheadlines.app.data.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<NewsDetailUIState>(NewsDetailUIState.Empty)
    val uiState : StateFlow<NewsDetailUIState> = _uiState.asStateFlow()

    fun initNewsModel(article: Article){
        if(_uiState.value !is NewsDetailUIState.Loaded){
            _uiState.value = NewsDetailUIState.Loading
        }
        article.let {
            _uiState.value = NewsDetailUIState.Loaded(article)
        }

    }

}

sealed class NewsDetailUIState{
    object Empty : NewsDetailUIState()
    object Loading : NewsDetailUIState()
    data class Loaded(val article: Article) : NewsDetailUIState()
}