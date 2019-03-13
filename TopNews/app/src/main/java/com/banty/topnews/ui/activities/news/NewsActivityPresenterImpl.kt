package com.banty.topnews.ui.activities.news

import android.util.Log
import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Banty on 12/03/19.
 */
class NewsActivityPresenterImpl(
    private val view: NewsActivityPresenter.View,
    private val newsRepository: NewsRepository
) : NewsActivityPresenter {

    override fun changeArticles(category: String) {
        getNewsHeadlines(category)
    }

    override fun handleNewsItemClicked(newsArticle: Article?) {
        view.startWebViewActivity(newsArticle?.url)
    }

    val TAG = "NewsActivityPresenter"

    override fun resume() {
        // show general news headlines on app start
        getNewsHeadlines("general")
    }

    private fun getNewsHeadlines(category: String) {
        view.hideUI()
        newsRepository.getNewsHeadlines("in", category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.articles?.apply {
                    Log.d(TAG, "News articles size : ${response?.articles.size}")
                    view.showUI()
                    view.setRecyclerView(response.articles)
                }
            }, { error ->
                Log.d(TAG, "Error : ${error.message}")
            })
    }

    override fun pause() {

    }

    override fun stop() {

    }

    override fun destroy() {

    }
}