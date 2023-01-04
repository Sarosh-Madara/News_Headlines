package com.newsheadlines.app.newsdetail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.newsheadlines.app.R
import com.newsheadlines.app.data.model.Article
import com.newsheadlines.app.databinding.FragmentNewsDetailBinding
import com.newsheadlines.app.newsdetail.viewmodel.NewsDetailUIState
import com.newsheadlines.app.newsdetail.viewmodel.NewsDetailViewModel
import com.newsheadlines.app.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {

    private lateinit var binding: FragmentNewsDetailBinding

    private val args: NewsDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<NewsDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initNewsModel(args.articleModel)
        observeNews()


    }

    private fun popupNews(articleModel: Article) {
        binding.txtTitle.text = articleModel.title
        binding.txtDescription.text = articleModel.description
        binding.txtAuthor.text = articleModel.author
        binding.txtSource.text = articleModel.source.name
        binding.txtNewsSource.text = articleModel.source.name
        binding.txtContent.text = articleModel.content
        binding.txtDate.text = String.format(
            "Published at: %s", articleModel.publishedAt.changeDateFormat(
                Constants.DATE_FORMAT_YMD_T_HMS_Z,
                Constants.DATE_FORMAT_M_D
            )
        )
        if (articleModel.urlToImage.isNotBlank()) {
            requireContext().loadImageFromURL(articleModel.urlToImage, binding.imgNewsItem)
        } else {
            binding.imgNewsItem.gone()
        }
    }

    private fun observeNews() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                when (it) {
                    NewsDetailUIState.Empty -> {}
                    is NewsDetailUIState.Loaded -> {
                        binding.progressBar.gone()
                        binding.layoutNews.visible()
                        popupNews(it.article)
                    }
                    NewsDetailUIState.Loading -> {
                        binding.progressBar.visible()
                    }
                }
            }
        }
    }

}