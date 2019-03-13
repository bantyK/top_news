package com.banty.topnews.ui.activities.news.recyclerview

import com.banty.topnews.datamodels.Article

/**
 * Created by Banty on 13/03/19.
 */
interface ItemClickListener {

    fun listItemClicked(newsArticle: Article?)
}