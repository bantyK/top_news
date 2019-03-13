package com.banty.topnews.repository.remote

import com.banty.topnews.datamodels.TopHeadlinesResponse
import com.banty.topnews.network.retrofit.NewsApiService
import com.banty.topnews.repository.NewsRepository
import io.reactivex.Observable

class RemoteNewsRepository constructor(val newsApiService: NewsApiService) : NewsRepository {
    override fun getNewsHeadlines(country: String, category: String): Observable<TopHeadlinesResponse> {
        return newsApiService.getTopHeadlines(country, "94e491f91fd24aa5bc4cc2d1915d849f", category)

    }


    override fun saveNewsHeadlines(newsArticle: Observable<TopHeadlinesResponse>) {
        // not required to be implemented in remote. Implemented by LocalRepository
    }

}
