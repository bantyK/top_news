package com.banty.topnews.repository.local

import com.banty.topnews.datamodels.TopHeadlinesResponse
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.repository.local.files.FileManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalNewsRepository(private val fileManager: FileManager) : NewsRepository {

    /**
     * Saves the news data into local storage
     * Uses RxJava to delegate the file writing operation to IO thread,
     * keeping the UI thread free
     * */
    override fun saveNewsHeadlines(newsArticle: Observable<TopHeadlinesResponse>) {
        newsArticle.subscribeOn(Schedulers.io())
            .subscribe {
                fileManager.saveObjectToFile(it)
            }

    }


    /**
     * Returns an observable which contains the data fetched from the file
     * */
    override fun getNewsHeadlines(country: String, category: String): Observable<TopHeadlinesResponse> {
        return Observable.just(fileManager.getObjectFromFile())
    }

}
