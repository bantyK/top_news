package com.banty.topnews.di.module

import android.content.Context
import com.banty.topnews.network.retrofit.NewsApiService
import com.banty.topnews.network.retrofit.RetrofitClientCreator
import com.banty.topnews.network.util.NetworkConnectivityUtil
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.repository.NewsRepositoryImpl
import com.banty.topnews.repository.local.LocalNewsRepository
import com.banty.topnews.repository.local.files.FileManager
import com.banty.topnews.repository.remote.RemoteNewsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Banty on 10/03/19.
 */
@Module
class RepositoryModule(val context: Context) {

    @Provides
    @Singleton
    fun provideNewsRepository(
        localNewsRepository: LocalNewsRepository,
        remoteNewsRepository: RemoteNewsRepository,
        networkConnectivityUtil: NetworkConnectivityUtil
    ): NewsRepository {
        return NewsRepositoryImpl(
            localNewsRepository,
            remoteNewsRepository,
            networkConnectivityUtil
        )
    }

    @Provides
    @Singleton
    fun provideLocalNewsRepository(fileManager: FileManager): LocalNewsRepository {
        return LocalNewsRepository(fileManager)
    }

    @Provides
    @Singleton
    fun provideRemoteNewsRepository(newsApiService: NewsApiService): RemoteNewsRepository {
        return RemoteNewsRepository(newsApiService)
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofitClient: Retrofit): NewsApiService {
        return retrofitClient.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        return RetrofitClientCreator.createRetrofitClient()
    }

    @Provides
    @Singleton
    fun provideNetworkUtil(): NetworkConnectivityUtil = NetworkConnectivityUtil(context)

    @Provides
    @Singleton
    fun provideFileManager(): FileManager = FileManager(context.applicationContext)


}