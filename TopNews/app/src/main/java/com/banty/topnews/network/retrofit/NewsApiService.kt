package com.banty.topnews.network.retrofit

import com.banty.topnews.datamodels.Article
import com.banty.topnews.datamodels.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

/**
 * Created by Banty on 10/03/19.
 */
interface NewsApiService {

    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country:String,
        @Query("apiKey") apiKey:String
    ) : Observable<TopHeadlinesResponse>
}