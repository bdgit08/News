package com.mobile.dtnews.viewModel

import com.mobile.dtnews.model.Article
import com.mobile.dtnews.model.ServiceApi
import com.mobile.dtnews.model.Source
import com.mobile.dtnews.paging.PageKeyedPagingSource

class ArticlePagingSource(private val api: ServiceApi, private val query: MutableMap<String,Any>) :
    PageKeyedPagingSource<Article>() {
    override suspend fun getResultFromService(page: Int): List<Article> {
        query["page"] = page
        query["pageSize"] = 30
        return api.getArticle(query).articles
    }

}