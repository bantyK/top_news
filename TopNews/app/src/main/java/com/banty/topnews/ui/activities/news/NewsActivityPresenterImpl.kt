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
    override fun changeArticles(country: String, category: String) {
        getNewsHeadlines(country, category, true)
    }

    /**
     * Asks view to show the Web view activity, passes the URL from the article object.
    * */
    override fun handleNewsItemClicked(newsArticle: Article?) {
        view.startWebViewActivity(newsArticle?.url)
    }

    override fun resume() {
        // show general news headlines on app start
        getNewsHeadlines(country, "general", false)
    }

    /**
     * Get the data from the repository. @param refresh indicates whether repository should return the cached data or
     * fetch data from server
    * */
    override fun getNewsHeadlines(country: String,category: String, refresh: Boolean) {
        view.hideUI()
        if (refresh) newsRepository.refreshNews()
        newsRepository.getNewsArticles(country, category, object : NewsRepository.LoadNewsCallback {
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

    /**
     * Handle the country selection/change
     * */
    override fun handleCountrySelection(selectedCountryCode: String, selectedCategory:String) {
        getNewsHeadlines(selectedCountryCode, selectedCategory, true)
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