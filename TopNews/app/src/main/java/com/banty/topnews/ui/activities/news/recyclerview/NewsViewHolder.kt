package com.banty.topnews.ui.activities.news.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.banty.topnews.R

class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    var titleTextView: TextView = view.findViewById(R.id.news_title)
    var contentTextView: TextView = view.findViewById(R.id.news_content)
    var sourceTextView: TextView = view.findViewById(R.id.news_source)
    var durationTextView: TextView = view.findViewById(R.id.news_duration)
    var newsThumbnail: ImageView = view.findViewById(R.id.news_thumb)

}
