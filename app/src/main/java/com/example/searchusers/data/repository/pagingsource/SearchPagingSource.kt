package com.example.searchusers.data.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchusers.data.api.RetrofitInterface
import com.example.searchusers.data.model.UserModel

class SearchPagingSource constructor(private val retrofitInterface: RetrofitInterface, private val search: String) : PagingSource<Int, UserModel>() {
    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return try {
            val page = params.key ?: 1
            val response = retrofitInterface.search(
                    q = search,
                    page = page)
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = when {
                    response.items.isEmpty() -> null
                    else -> page + 1
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}