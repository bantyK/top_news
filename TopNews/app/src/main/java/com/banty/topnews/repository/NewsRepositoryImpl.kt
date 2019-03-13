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

    override fun getNewsHeadlines(country: String, category: String, refreshLocal: Boolean): Observable<TopHeadlinesResponse> {
        return if(refreshLocal) {
            refreshHeadlines(country, category)
        } else {
            localNewsRepository.getNewsHeadlines(country, category, false)
        }
    }

    override fun saveNewsHeadlines(newsArticle: Observable<TopHeadlinesResponse>) {
        localNewsRepository.saveNewsHeadlines(newsArticle)
    }

    fun refreshHeadlines(country : String, category: String): Observable<TopHeadlinesResponse> {
        return if(networkUtil.isNetworkAvailable()) {
            // fetch new data from server
            val newsHeadlines = remoteNewsRepository.getNewsHeadlines(country, category, true)
            // save the data in local storage for offline use
            saveNewsHeadlines(newsHeadlines)

            newsHeadlines
        } else {
            localNewsRepository.getNewsHeadlines(country, category, false)
        }
    }
}