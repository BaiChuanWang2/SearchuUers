package com.example.searchusers.di

import com.example.searchusers.data.repository.SearchRepository
import com.example.searchusers.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SearchViewModel(get(), get()) }
    single { SearchRepository(get()) }
}