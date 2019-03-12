package com.banty.topnews.ui.activities.news

import com.banty.topnews.datamodels.Article
import com.banty.topnews.ui.presenter.BasePresenter

/**
 * Created by Banty on 12/03/19.
 */
interface NewsActivityPresenter : BasePresenter {

    interface View {
        fun setRecyclerView(articles: List<Article>?)
    }
}