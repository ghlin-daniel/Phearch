package com.guanhaolin.pearch

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.guanhaolin.pearch.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PhearchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PhearchApp)
            modules(koinModules)
        }
        Fresco.initialize(this)
    }
}
