package com.banty.topnews.datamodels

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("source") val source: Source,
    @SerializedName("authour") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val imageUrl: String,
    @SerializedName("publishedAt") val date: String
)

data class Source(
    @SerializedName("id") val newsSourceId: String,
    @SerializedName("name") val newsSourceName: String
)
