package com.banty.topnews.ui.activities.news.recyclerview

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.banty.topnews.R

class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val parentView : ConstraintLayout = view.findViewById(R.id.parent_view)
    val titleTextView: TextView = view.findViewById(R.id.news_title)
    val contentTextView: TextView = view.findViewById(R.id.news_content)
    val sourceTextView: TextView = view.findViewById(R.id.news_source)
    val durationTextView: TextView = view.findViewById(R.id.news_duration)
    val newsThumbnail: ImageView = view.findViewById(R.id.news_thumb)

}
