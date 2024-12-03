package com.guanhaolin.pearch.di

import com.guanhaolin.pearch.data.MediaRepository
import com.guanhaolin.pearch.data.MediaRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single {
        MediaRepositoryImpl(get())
    } bind MediaRepository::class
}