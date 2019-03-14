package com.banty.topnews.repository

import com.banty.topnews.datamodels.Article

/**
 * Created by Banty on 14/03/19.
 */
interface NewsDataCallback {

    fun getNewsData(articles: List<Article>)

    fun error(error: String)
}