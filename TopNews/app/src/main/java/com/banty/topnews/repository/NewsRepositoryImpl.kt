package com.banty.topnews.repository

import com.banty.topnews.datamodels.TopHeadlinesResponse
import com.banty.topnews.network.util.NetworkConnectivityUtil
import com.banty.topnews.repository.local.LocalNewsRepository
import com.banty.topnews.repository.remote.RemoteNewsRepository
import io.reactivex.Observable

/**
 * Created by Banty on 10/03/19.
 */
class NewsRepositoryImpl(
    private val localNewsRepository: LocalNewsRepository,
    private val remoteNewsRepository: RemoteNewsRepository,
    private val networkUtil: NetworkConnectivityUtil
) : NewsRepository {
    override fun getNewsHeadlines(country: String, category: String): Observable<TopHeadlinesResponse> {
        return if (networkUtil.isNetworkAvailable()) {
            // fetch the data from server
            val newsHeadlines = remoteNewsRepository.getNewsHeadlines(country, category)
            // save the data in local storage for offline use
            saveNewsHeadlines(newsHeadlines)
            // returns the data
            newsHeadlines
        } else {
            localNewsRepository.getNewsHeadlines(country, category)
        }
    }

    override fun saveNewsHeadlines(newsArticle: Observable<TopHeadlinesResponse>) {
        localNewsRepository.saveNewsHeadlines(newsArticle)
    }
}