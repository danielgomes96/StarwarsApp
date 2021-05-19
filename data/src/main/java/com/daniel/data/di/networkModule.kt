package com.daniel.data.di

import com.daniel.data.service.StarwarsService
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory {
        getStarwarsService(
            get<Retrofit>()
        )
    }

    single {
        createStarwarsService(
            get()
        )
    }

    factory {
        createOkHttpClient()
    }
}

private fun getStarwarsService(retrofit: Retrofit): StarwarsService =
    retrofit.create(StarwarsService::class.java)

private fun createStarwarsService(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl("https://swapi.dev/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun createOkHttpClient(): OkHttpClient {
    val timeoutInSeconds = 10L
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}
