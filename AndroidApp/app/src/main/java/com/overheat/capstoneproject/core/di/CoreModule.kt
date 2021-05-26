package com.overheat.capstoneproject.core.di

import androidx.room.Room
import com.overheat.capstoneproject.core.data.source.SkinCancerRepository
import com.overheat.capstoneproject.core.data.source.local.LocalDataSource
import com.overheat.capstoneproject.core.data.source.local.room.SkinCancerDatabase
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.domain.repository.ISkinCancerRepository
import com.overheat.capstoneproject.core.utils.AppExecutors
import com.overheat.capstoneproject.core.utils.JsonHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory {
        get<SkinCancerDatabase>().skinCancerDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            SkinCancerDatabase::class.java,
            "SkinCancer.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val remoteDataSourceModule = module {
    single {
        JsonHelper(get())
    }
    single {
        RemoteDataSource(get())
    }
}

val repositoryModule = module {
    single {
        LocalDataSource(get())
    }
    factory {
        AppExecutors()
    }
    single<ISkinCancerRepository> {
        SkinCancerRepository(get(), get(), get())
    }
}