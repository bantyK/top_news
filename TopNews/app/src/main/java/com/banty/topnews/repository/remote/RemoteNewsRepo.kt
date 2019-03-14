package com.banty.topnews.repository.remote

import com.banty.topnews.datamodels.Article
import com.banty.topnews.network.retrofit.NewsApiService
import com.banty.topnews.repository.NewsRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Banty on 14/03/19.
 */

/*
* Fetches the news articles from server.
*
* Uses RxJava to handle the threading
* */
class RemoteNewsRepo(
    private val newsApiService: NewsApiService,
    private val ioSchedulers: Scheduler,
    private val androidSchedulers: Scheduler
    ) : NewsRepository {

    override fun getNewsArticles(country: String, category: String, callback: NewsRepository.LoadNewsCallback) {
        newsApiService.getTopHeadlines(country, category)
            .subscribeOn(ioSchedulers)
            .observeOn(androidSchedulers)
            .subscribe({
                if (it.articles == null || it.articles.isEmpty()) {
                    callback.onNewsFailedToLoad()
                } else {
                    callback.onNewsLoaded(it.articles)
                }
            }, {
                callback.onNewsFailedToLoad()
            })
    }

    override fun saveNewsArticles(articles: List<Article>) {
        // not required but NewsRepoImpl handles the task of saving the news articles
        // into local cache
    }

    override fun refreshNews() {
        // not required but NewsRepoImpl handles the task of refreshing the news articles.
    }
}