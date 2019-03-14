package com.banty.topnews.repository.local

import com.banty.topnews.datamodels.Article
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.repository.local.files.FileManager

/**
 * Created by Banty on 14/03/19.
 */
class LocalNewsRepo(private val fileManager: FileManager) : NewsRepository {

    /**
     * Reads the news articles from file system and returns via the LoadNewsCallback.
     * calls onNewsFailedToLoad() if article list is empty
    * */
    override fun getNewsArticles(country:String, category: String, callback: NewsRepository.LoadNewsCallback) {
        val articlesFromFile = this.fileManager.getArticlesFromFile()
        if (articlesFromFile == null || articlesFromFile.isEmpty()) {
            callback.onNewsFailedToLoad()
        } else {
            callback.onNewsLoaded(articlesFromFile)
        }
    }

    /**
     * Update the article list in local storage.
     *
    * */
    override fun saveNewsArticles(articles: List<Article>) {
        fileManager.saveArticlesToFile(articles)
    }

    override fun refreshNews() {
        // not required but NewsRepoImpl handles the task of refreshing the news articles.
    }
}