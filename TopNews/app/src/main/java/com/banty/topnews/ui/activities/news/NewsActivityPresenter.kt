package com.banty.topnews.ui.activities.news

import com.banty.topnews.datamodels.Article
import com.banty.topnews.ui.presenter.BasePresenter

/**
 * Created by Banty on 12/03/19.
 */
interface NewsActivityPresenter : BasePresenter {
    fun handleNewsItemClicked(newsArticle: Article?)
    fun changeArticles(country: String, category: String)

    interface View {
        fun startWebViewActivity(url: String?)
        fun showUI()
        fun hideUI()
        fun showDataFetchErrorMessage()
    }

    fun getNewsHeadlines(country:String, category: String, refresh:Boolean)
    fun handleCountrySelection(selectedCountryCode: String, selectedCategory: String)
}