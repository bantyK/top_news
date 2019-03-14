package com.banty.topnews.repository

import android.util.Log
import com.banty.topnews.datamodels.Article

/**
 * Created by Banty on 14/03/19.
 */
class NewsRepoImpl(private val localNewsRepo: NewsRepository, private val remoteNewsRepo: NewsRepository) :
    NewsRepository {

    private val TAG = "NewsRepo"


    // this represent the state of cache in local storage.
    // will be updated if fresh data from server needs to be fetched
    private var isCacheDirty = false


    /**
     * first checks the local storage, if data is avaiable directly returns the locally saved list of articles.
     * */
    override fun getNewsArticles(category: String, callback: NewsRepository.LoadNewsCallback) {
        if (isCacheDirty) {
            // cache has expired, so fetch news articles from server
            fetchNewsFromRemoteDataSource(category, callback)
        } else {
            // cache is not expired, try to fetch the locally saved news articles.
//            Log.d(TAG, "Fetching local data")
            localNewsRepo.getNewsArticles(category, object : NewsRepository.LoadNewsCallback {
                override fun onNewsLoaded(articles: List<Article>) {
                    // articles found in local storage, return them
                    callback.onNewsLoaded(articles)
                }

                override fun onNewsFailedToLoad() {
                    // cannot fetch data from local cache. Fetch it from server
                    fetchNewsFromRemoteDataSource(category, callback)
                }

            })
        }
    }

    /**
     * This method calls the remote server to fetch news articles from newsAPI and update the cache
     * */
    private fun fetchNewsFromRemoteDataSource(category: String, callback: NewsRepository.LoadNewsCallback) {
//        Log.d(TAG, "Fetching data from server")
        remoteNewsRepo.getNewsArticles(category, loadNewsCallback(callback))
    }

    private fun loadNewsCallback(callback: NewsRepository.LoadNewsCallback): NewsRepository.LoadNewsCallback {
        return object : NewsRepository.LoadNewsCallback {
            override fun onNewsLoaded(articles: List<Article>) {
                // data is fetched, update the local cache.
                saveNewsArticles(articles)
                callback.onNewsLoaded(articles)
            }

            override fun onNewsFailedToLoad() {
                // data cannot be fetched from server, calls the onFailed method
                callback.onNewsFailedToLoad()
            }

        }
    }

    /**
     * This method updates the local cache. Calls LocalRepo for performing the file ops
     * */
    override fun saveNewsArticles(articles: List<Article>) {
        localNewsRepo.saveNewsArticles(articles)
    }

    /**
    * This method is called when local data needs to be updated.
    * */
    override fun refreshNews() {
        isCacheDirty = true
    }
}