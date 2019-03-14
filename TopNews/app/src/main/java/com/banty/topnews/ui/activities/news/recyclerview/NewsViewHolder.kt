package com.banty.topnews.ui.activities.news.recyclerview

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.banty.topnews.R



/**
 * View holder to be used by Recycler view for recycling the list items.
 * */
class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    // top view parent
    val parentView: ConstraintLayout = view.findViewById(R.id.parent_view)

    // text view to display the title
    val titleTextView: TextView = view.findViewById(R.id.news_title)

    // text view which display the content
    val contentTextView: TextView = view.findViewById(R.id.news_content)

    // text view which displays the news source
    val sourceTextView: TextView = view.findViewById(R.id.news_source)

    // text view which displays the date when news is published
    val durationTextView: TextView = view.findViewById(R.id.news_duration)

    // image view to display the image associated with the story
    val newsThumbnail: ImageView = view.findViewById(R.id.news_thumb)

}
