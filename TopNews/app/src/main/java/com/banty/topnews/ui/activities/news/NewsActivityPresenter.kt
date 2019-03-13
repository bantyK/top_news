package com.banty.topnews.ui.activities.news

import com.banty.topnews.datamodels.Article
import com.banty.topnews.ui.presenter.BasePresenter

/**
 * Created by Banty on 12/03/19.
 */
interface NewsActivityPresenter : BasePresenter {
    fun handleNewsItemClicked(newsArticle: Article?)
    fun changeArticles(category: String)

    interface View {
        fun startWebViewActivity(url: String?)
        fun showUI()
        fun hideUI()
    }

    fun getNewsHeadlines(category: String, refresh:Boolean)
}