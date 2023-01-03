package com.newsheadlines.app.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.newsheadlines.app.R
import com.newsheadlines.app.data.model.Article
import com.newsheadlines.app.databinding.FragmentNewsListBinding
import com.newsheadlines.app.newslist.viewmodel.NewsListUIState
import com.newsheadlines.app.newslist.viewmodel.NewsListViewModel
import com.newsheadlines.app.util.gone
import com.newsheadlines.app.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment(R.layout.fragment_news_list){

    private lateinit var binding : FragmentNewsListBinding

    private val viewModel by viewModels<NewsListViewModel>()

    private lateinit var newsListAdapter: NewsListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadTopNews("techcrunch")
        observeNews()

    }

    private fun observeNews() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                when (it) {
                    NewsListUIState.Empty -> {}
                    is NewsListUIState.Loaded -> {

                        binding.progressBar.gone()
                        binding.layoutNoNews.gone()
                        binding.listNews.visible()
                        newsListAdapter = NewsListAdapter(requireContext(),it.newsList.toTypedArray()){
                            setupNavigation(it)
                        }
                        binding.listNews.adapter = newsListAdapter
                    }
                    NewsListUIState.Loading -> {
                        binding.progressBar.visible()
                    }
                    NewsListUIState.NoNews -> {
                        binding.progressBar.gone()
                        binding.listNews.gone()
                        binding.layoutNoNews.visible()
                    }
                }

            }
        }
    }

    private fun setupNavigation(article: Article) {
        findNavController().navigate(NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(article))
    }

}