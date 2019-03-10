package com.banty.topnews.datamodels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Banty on 10/03/19.
 */
data class TopHeadlinesResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("totalResults") val totalResultCount: Int?,
    @SerializedName("articles") val articles: List<Article>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(Article)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeValue(totalResultCount)
        parcel.writeTypedList(articles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopHeadlinesResponse> {
        override fun createFromParcel(parcel: Parcel): TopHeadlinesResponse {
            return TopHeadlinesResponse(parcel)
        }

        override fun newArray(size: Int): Array<TopHeadlinesResponse?> {
            return arrayOfNulls(size)
        }
    }
}