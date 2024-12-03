package com.guanhaolin.pearch.di

import com.google.gson.Gson
import com.guanhaolin.pearch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Retrofit
            .Builder()
            .client(get())
            .addConverterFactory(get<GsonConverterFactory>())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }


    single { GsonConverterFactory.create(get()) }

    single { Gson().newBuilder().setLenient().create() }

    single {
        OkHttpClient().newBuilder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
        }.build()
    }

    single {
        HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }
}
