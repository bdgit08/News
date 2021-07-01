package com.mobile.dtnews.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mobile.dtnews.model.Article
import com.mobile.dtnews.paging.PageListAdapter
import com.mobile.dtnews.paging.PageListFragment
import com.mobile.dtnews.viewModel.DefaultViewModelFactory
import com.mobile.dtnews.viewModel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticleFragment : PageListFragment<Article>() {
    private lateinit var viewModel : NewsViewModel
    private var job : Job?= null
    private var query = mutableMapOf<String,Any>()

    override fun pageListAdapter(): PageListAdapter<Article> {
        viewModel = ViewModelProvider(requireActivity(), DefaultViewModelFactory()).get(NewsViewModel::class.java)
        if (viewModel.sources.isNotEmpty()){
            query["sources"] = viewModel.sources
        }
        return ArticleAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.keyword.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { keyword ->
                query["q"] = keyword
                loadItems()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadItems()
    }

    private fun loadItems(){
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getArticle(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

}