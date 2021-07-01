package com.mobile.dtnews.viewModel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mobile.dtnews.model.Article
import com.mobile.dtnews.model.Result
import com.mobile.dtnews.model.ServiceApi
import com.mobile.dtnews.model.SourceResult
import kotlinx.coroutines.flow.Flow

class DefaultNewsRepository(private val api: ServiceApi) : NewsRepository {
    override suspend fun getSource(category: String): Result<SourceResult> =
        try {
            val map = mapOf(
                "category" to category,
                "apiKey" to apiKey, "language" to "en"
            )
            val result = api.getSource(map)
             Result.Success(result)
        } catch (ex: Throwable) {
             Result.Error(ex)
        }

    override fun getArticle(query: MutableMap<String, Any>): Flow<PagingData<Article>> =
        Pager(PagingConfig(30)){
            query["apiKey"] = apiKey
            query["language"] = "en"
            ArticlePagingSource(api, query)
        }.flow

    companion object {
        const val apiKey = "5bafcf68425c413ba4f232121a0baa27"
    }

}