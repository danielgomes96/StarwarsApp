package com.daniel.starwarsapp

import android.app.Application
import com.daniel.data.di.networkModule
import com.daniel.data.di.repositoryModule
import com.daniel.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(domainModule, networkModule, repositoryModule))
        }
    }
}
