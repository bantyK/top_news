package com.banty.topnews.repository

import com.banty.topnews.datamodels.Article
import com.banty.topnews.datamodels.TopHeadlinesResponse
import io.reactivex.Observable

/**
 * Created by Banty on 10/03/19.
 *
 * Uses Repository pattern for providing the data objects
 * Hides the details of data retrieval mechanism from the client.
 * Interface which will be exposed to caller modules for fetching the news data.
 */
interface NewsRepository {

    interface LoadNewsCallback {
        fun onNewsLoaded(articles:List<Article>)

        fun onNewsFailedToLoad()
    }

    fun getNewsArticles(country:String, category : String, callback: LoadNewsCallback)

    fun saveNewsArticles(articles: List<Article>)

    fun refreshNews()
}