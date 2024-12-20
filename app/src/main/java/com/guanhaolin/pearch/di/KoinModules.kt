package com.guanhaolin.pearch.di

import org.koin.dsl.module

val koinModules = module {
    includes(networkModule, serviceModule, repositoryModule, viewModelModule)
}