package com.guanhaolin.pearch.di

import com.guanhaolin.pearch.ui.MediaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MediaViewModel)
}