package com.kurniawan.capstoneproject

import android.app.Application
import com.kurniawan.capstoneproject.core.di.databaseModule
import com.kurniawan.capstoneproject.core.di.networkModule
import com.kurniawan.capstoneproject.core.di.repositoryModule
import com.kurniawan.capstoneproject.di.useCaseModule
import com.kurniawan.capstoneproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}