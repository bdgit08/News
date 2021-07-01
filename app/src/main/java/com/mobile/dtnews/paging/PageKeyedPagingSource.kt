package com.mobile.dtnews.paging

import androidx.paging.PagingSource

abstract class PageKeyedPagingSource<T:Any> : PagingSource<Int,T>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val data = getResultFromService(page)
            LoadResult.Page(
                    data = data.toList(),
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    abstract suspend fun getResultFromService(page : Int): List<T>




}