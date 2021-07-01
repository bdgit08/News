package com.mobile.dtnews.model

import com.squareup.moshi.Json

data class SourceResult(
    @field:Json(name = "status")
    var status: String,
    @field:Json(name = "sources")
    var sources: List<Source>
)

data class Source(
    @field:Json(name = "id")
    var id:String,
    @field:Json(name = "name")
    var name: String,
    @field:Json(name = "description")
    var description : String
)

data class ArticleResult(
    @field:Json(name = "status")
    var status: String,
    @field:Json(name = "totalResults")
    var totalResults: Int,
    @field:Json(name = "articles")
    var articles: List<Article>
)

data class Article(
    var name: String,
    @field:Json(name = "description")
    var desc: String,
    @field:Json(name = "urlToImage")
    var image: String,
    @field:Json(name = "author")
    var author: String,
    @field:Json(name = "title")
    var title: String,
    @field:Json(name = "publishedAt")
    var publishedAt: String,
    @field:Json(name = "content")
    var content: String,
    @field:Json(name = "url")
    var url: String,
    @field:Json(name = "source")
    var source: Source
)


sealed class Result<out T : Any> {
    class Success<out T : Any>(val data: T) : Result<T>()
    class Error(val exception: Throwable) : Result<Nothing>()
}