package com.mobile.dtnews.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit

interface ServiceApi {

    @GET("sources")
    suspend fun getSource(@QueryMap query : Map<String,@JvmSuppressWildcards Any>) : SourceResult

    @GET("everything")
    suspend fun getArticle(@QueryMap query : Map<String,@JvmSuppressWildcards Any>) : ArticleResult

    companion object {
        fun instanceApi(): ServiceApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder().apply {
                connectTimeout(5, TimeUnit.SECONDS)
                readTimeout(5, TimeUnit.SECONDS)
                writeTimeout(5, TimeUnit.SECONDS)
                addInterceptor(interceptor)
            }
            val retrofit = Retrofit.Builder().apply {
                baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
                client(httpClient.build())
            }
            return retrofit.build().create(ServiceApi::class.java)
        }
    }
}