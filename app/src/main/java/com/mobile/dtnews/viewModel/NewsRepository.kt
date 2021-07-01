package com.mobile.dtnews.viewModel

import androidx.paging.PagingData
import com.mobile.dtnews.model.Article
import com.mobile.dtnews.model.Result
import com.mobile.dtnews.model.Source
import com.mobile.dtnews.model.SourceResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getSource(category : String) : Result<SourceResult>
    fun getArticle(query : MutableMap<String,Any>) : Flow<PagingData<Article>>

}