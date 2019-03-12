package com.banty.topnews.ui.activities.news.recyclerview

/**
 * Created by Banty on 13/03/19.
 */

/**
 * Returns the title of the news after removing the title at the end as returned by the API.
 * News source is shown in a different UI element.
 * */
fun getFormattedNewsTitle(title: String?): String {
    val lastIndexOf = title?.lastIndexOf("-")!!
    return title.substring(0, lastIndexOf - 1)
}

/**
 * Returns the date part from the UTC date format returned by the API
* */
fun getFormattedDuration(date: String?): String? {
    return date?.split("T")?.get(0)
}