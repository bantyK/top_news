package com.banty.topnews.ui.activities.news

import android.util.Log
import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.viewmodel.HeadlinesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Banty on 12/03/19.
 */
class NewsActivityPresenterImpl(
    private val view: NewsActivityPresenter.View,
    private val newsRepository: NewsRepository,
    private val headlinesViewModel: HeadlinesViewModel,
    private val country: String
) : NewsActivityPresenter {

    override fun changeArticles(category: String) {
        getNewsHeadlines(category, true)
    }

    override fun handleNewsItemClicked(newsArticle: Article?) {
        view.startWebViewActivity(newsArticle?.url)
    }

    val TAG = "NewsActivityPresenter"

    override fun resume() {
        // show general news headlines on app start
        getNewsHeadlines("general", false)
    }

    override fun getNewsHeadlines(category: String, refresh: Boolean) {
        view.hideUI()
        newsRepository.getNewsHeadlines(country, category, refresh)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.articles?.apply {
                    //set the view model with the new value
                    headlinesViewModel.setHeadlinesViewModel(response?.articles)
                    view.showUI()
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