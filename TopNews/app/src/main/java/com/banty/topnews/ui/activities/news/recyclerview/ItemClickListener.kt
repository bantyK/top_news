package com.banty.topnews.ui.activities.news.recyclerview

import com.banty.topnews.datamodels.Article

/**
 * Created by Banty on 13/03/19.
 *
 * This will be used by the recycler view to send the news article which was clicked by user from the list
 */

interface ItemClickListener {

    fun listItemClicked(newsArticle: Article?)
}