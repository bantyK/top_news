package com.banty.topnews.repository

import com.banty.topnews.datamodels.TopHeadlinesResponse
import io.reactivex.Observable

/**
 * Created by Banty on 10/03/19.
 *
 * Uses Repository pattern for providing the data objects
 * Hides the details of data retrieval mechanism from the client.
 * Interface which will be exposed to caller modules for fetching the news data.
 */
interface NewsRepository {

    /**
     * Method to fetch the headlines data with category
    * */
    fun getNewsHeadlines(country: String, category: String, refreshLocal: Boolean): Observable<TopHeadlinesResponse>

    /**
     * Method to save the headlines data in local memory
     * */
    fun saveNewsHeadlines(newsArticle: Observable<TopHeadlinesResponse>)


}