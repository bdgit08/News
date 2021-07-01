package com.mobile.dtnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.mobile.dtnews.viewModel.DefaultViewModelFactory
import com.mobile.dtnews.viewModel.NewsViewModel

class ArticleActivity : AppCompatActivity() {
    private lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val source = intent.getStringExtra(SOURCE)
        val name = intent.getStringExtra(NAME)
        title = name
        viewModel = ViewModelProvider(this, DefaultViewModelFactory()).get(NewsViewModel::class.java)
        source?.let {
            viewModel.sources = it
        }
        setContentView(R.layout.activity_article)
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty() || !newText.isNullOrBlank()){
                    viewModel.onSearch(newText)
                }
                return true
            }
        })
    }

    companion object {
        const val SOURCE = "SOURCE"
        const val NAME = "NAME_SOURCE"
    }
}