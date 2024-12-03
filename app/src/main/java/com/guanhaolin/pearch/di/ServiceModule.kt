package com.guanhaolin.pearch.di

import com.guanhaolin.pearch.api.PixabayService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { get<Retrofit>().create(PixabayService::class.java) }
}