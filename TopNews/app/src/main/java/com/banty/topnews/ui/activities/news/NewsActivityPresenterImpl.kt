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


    /**
     * Called by View when user selects an option from the side drawer menu.
     * */
    override fun changeArticles(category: String) {
        getNewsHeadlines(category, true)
    }

    /**
     * Asks view to show the Web view activity, passes the URL from the article object.
    * */
    override fun handleNewsItemClicked(newsArticle: Article?) {
        view.startWebViewActivity(newsArticle?.url)
    }

    override fun resume() {
        // show general news headlines on app start
        getNewsHeadlines("general", false)
    }

    /**
     * Get the data from the repository. @param refresh indicates whether repository should return the cached data or
     * fetch data from server
    * */
    override fun getNewsHeadlines(category: String, refresh: Boolean) {
        view.hideUI()
        if (refresh) newsRepository.refreshNews()
        newsRepository.getNewsArticles(category, object : NewsRepository.LoadNewsCallback {
            override fun onNewsLoaded(articles: List<Article>) {
                view.showUI()
                // update the view model when repository returns the data
                newsViewModel.setHeadlinesViewModel(articles)
            }

            override fun onNewsFailedToLoad() {
                // shows the error message in the UI when repository do not return any data
                view.showDataFetchErrorMessage()
            }
        })
    }

    override fun pause() {
        // implementation not required
    }

    override fun stop() {
        // implementation not required
    }

    override fun destroy() {
        // implementation not required
    }
}