package com.overheat.capstoneproject

import android.app.Application
import com.overheat.capstoneproject.core.di.databaseModule
import com.overheat.capstoneproject.core.di.remoteDataSourceModule
import com.overheat.capstoneproject.core.di.repositoryModule
import com.overheat.capstoneproject.ui.di.useCaseModule
import com.overheat.capstoneproject.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
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
                    remoteDataSourceModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}