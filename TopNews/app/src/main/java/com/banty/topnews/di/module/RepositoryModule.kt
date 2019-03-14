package com.banty.topnews.di.module

import android.content.Context
import com.banty.topnews.network.retrofit.NewsApiService
import com.banty.topnews.network.retrofit.RetrofitClientCreator
import com.banty.topnews.repository.NewsRepoImpl
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.repository.local.LocalNewsRepo
import com.banty.topnews.repository.local.files.FileManager
import com.banty.topnews.repository.remote.RemoteNewsRepo
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
        localNewsRepository: LocalNewsRepo,
        remoteNewsRepository: RemoteNewsRepo
    ): NewsRepository {
        return NewsRepoImpl(
            localNewsRepository,
            remoteNewsRepository
        )
    }

    @Provides
    @Singleton
    fun provideLocalNewsRepository(fileManager: FileManager): LocalNewsRepo {
        return LocalNewsRepo(fileManager)
    }

    @Provides
    @Singleton
    fun provideRemoteNewsRepository(newsApiService: NewsApiService): RemoteNewsRepo {
        return RemoteNewsRepo(newsApiService)
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
    fun provideFileManager(): FileManager = FileManager(context.applicationContext)


}