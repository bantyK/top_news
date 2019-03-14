package com.banty.topnews.ui.activities.news

import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.viewmodel.NewsViewModel

/**
 * Created by Banty on 12/03/19.
 */
class NewsActivityPresenterImpl(
    private val view: NewsActivityPresenter.View,
    private val newsRepository: NewsRepository,
    private val newsViewModel: NewsViewModel,
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
        if (refresh) newsRepository.refreshNews()
        newsRepository.getNewsArticles(category, object : NewsRepository.LoadNewsCallback {
            override fun onNewsLoaded(articles: List<Article>) {
                view.showUI()
                newsViewModel.setHeadlinesViewModel(articles)
            }

            override fun onNewsFailedToLoad() {
                view.showDataFetchErrorMessage()
            }
        })
    }

    override fun pause() {

    }

    override fun stop() {

    }

    override fun destroy() {

    }
}