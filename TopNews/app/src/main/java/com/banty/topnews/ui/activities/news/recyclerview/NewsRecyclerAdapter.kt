package com.banty.topnews.ui.activities.news.recyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.banty.topnews.R
import com.banty.topnews.datamodels.Article
import com.bumptech.glide.Glide

/**
 * Created by Banty on 12/03/19.
 */
class NewsRecyclerAdapter constructor(
    private val articles: List<Article>?,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    private val stringFormatter = Formatter()

    /**
     * Inflate the layout of a single item of recycler view
     * */
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NewsViewHolder {
        val layoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.single_news_list_item, parent, false)
        return NewsViewHolder(view)

    }

    /**
     * Return the count of items to be displayed
    * */
    override fun getItemCount(): Int =
        articles?.size ?: 0


    /**
     * Populate each item view with article data
     * */
    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        // get an article from the list and the position variable
        val newsArticle = articles?.get(position)

        // populate the news articles data into UI
        viewHolder.titleTextView.text = stringFormatter.getFormattedNewsTitle(newsArticle?.title)
        viewHolder.contentTextView.text = newsArticle?.description
        viewHolder.sourceTextView.text = newsArticle?.source?.newsSourceName
        viewHolder.durationTextView.text = stringFormatter.getFormattedDuration(newsArticle?.date)

        // use Glide to load the image and handle image caching
        Glide.with(viewHolder.newsThumbnail.context)
            .load(newsArticle?.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .into(viewHolder.newsThumbnail)

        // click listener which makes the entire view clickable, send the article which was clicked
        // to the listener.
        viewHolder.parentView.setOnClickListener { itemClickListener.listItemClicked(newsArticle) }

    }

}