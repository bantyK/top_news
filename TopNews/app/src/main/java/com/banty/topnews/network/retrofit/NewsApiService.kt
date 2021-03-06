package com.banty.topnews.network.retrofit

import com.banty.topnews.datamodels.TopHeadlinesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Banty on 10/03/19.
 */
interface NewsApiService {

    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String = "",
        @Query("apiKey") apiKey: String = "94e491f91fd24aa5bc4cc2d1915d849f"
    ): Observable<TopHeadlinesResponse>
}