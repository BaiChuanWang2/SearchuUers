package com.example.searchusers.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.searchusers.data.api.RetrofitInterface
import com.example.searchusers.data.repository.pagingsource.SearchPagingSource

class SearchRepository constructor(private val retrofitInterface: RetrofitInterface) {
    fun search(search: String) = Pager(PagingConfig(
            pageSize = 20,
            prefetchDistance = 4)) {
        SearchPagingSource(retrofitInterface, search)
    }.flow
}