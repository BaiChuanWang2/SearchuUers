package com.example.searchusers.di

import com.example.searchusers.BuildConfig
import com.example.searchusers.common.config.ApiConfig
import com.example.searchusers.data.api.RetrofitInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single { initRetrofitInterface() }
}

private fun initRetrofitInterface() = initRetrofit().create(RetrofitInterface::class.java)

private fun initRetrofit(): Retrofit {
    val okHttpClient = initOkHttpClient().build()
    return Retrofit.Builder()
        .baseUrl(ApiConfig.DOMAIN)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun initOkHttpClient(): OkHttpClient.Builder {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = when {
        BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
    }
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .retryOnConnectionFailure(true)
        .connectTimeout(ApiConfig.TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(ApiConfig.TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(ApiConfig.TIMEOUT, TimeUnit.SECONDS)
}