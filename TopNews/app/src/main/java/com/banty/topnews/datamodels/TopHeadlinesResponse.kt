package com.banty.topnews.datamodels

import com.google.gson.annotations.SerializedName

/**
 * Created by Banty on 10/03/19.
 */
data class TopHeadlinesResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResultCount: Int,
    @SerializedName("articles") val articles: List<Article>
)