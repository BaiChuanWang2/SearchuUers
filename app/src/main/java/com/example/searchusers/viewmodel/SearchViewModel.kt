package com.example.searchusers.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchusers.R
import com.example.searchusers.data.repository.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class SearchViewModel(application: Application, searchRepository: SearchRepository) : AndroidViewModel(application) {
    private val search = MutableLiveData<String>()
    val displaySearchText = MutableLiveData<String>()
    private val channel = Channel<Unit>(Channel.CONFLATED)

    @FlowPreview
    @OptIn(ExperimentalCoroutinesApi::class)
    val usersList = flowOf(channel.receiveAsFlow()
            .map {
                PagingData.empty()
            },
            search.asFlow()
            .flatMapLatest {
                searchRepository.search(it)
            }.cachedIn(viewModelScope))
            .flattenMerge(2)

    fun searchClick() {
        search.value = displaySearchText.value
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivClear -> {
                displaySearchText.value = ""
            }
        }
    }
}