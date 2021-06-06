package com.overheat.capstoneproject.core.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.overheat.capstoneproject.core.data.source.SkinCancerRepository
import com.overheat.capstoneproject.core.data.source.local.LocalDataSource
import com.overheat.capstoneproject.core.data.source.local.room.SkinCancerDatabase
import com.overheat.capstoneproject.core.data.source.remote.RemoteDataSource
import com.overheat.capstoneproject.core.data.source.remote.network.ApiService
import com.overheat.capstoneproject.core.domain.repository.ISkinCancerRepository
import com.overheat.capstoneproject.core.utils.AppExecutors
import com.overheat.capstoneproject.core.utils.JsonHelper
import com.overheat.capstoneproject.core.utils.SkinCancerPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.101.238.135:5000/")
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            ))
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
    single {
        SkinCancerPreferences(get())
    }
    single {
        RemoteDataSource(get(), get(), get())
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
        SkinCancerRepository(get(), get(), get(), get())
    }
}