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
class NewsRecyclerAdapter constructor(private val articles: List<Article>?, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NewsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NewsViewHolder {
        val layoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.single_news_list_item, parent, false)
        return NewsViewHolder(view)

    }

    override fun getItemCount(): Int =
        articles?.size ?: 0


    override fun onBindViewHolder(viewHolder: NewsViewHolder, position: Int) {
        val newsArticle = articles?.get(position)
        viewHolder.titleTextView.text = getFormattedNewsTitle(newsArticle?.title)
        viewHolder.contentTextView.text = newsArticle?.description
        viewHolder.sourceTextView.text = newsArticle?.source?.newsSourceName
        viewHolder.durationTextView.text = getFormattedDuration(newsArticle?.date)
        Glide.with(viewHolder.newsThumbnail.context)
            .load(newsArticle?.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.default_image)
            .into(viewHolder.newsThumbnail)

        viewHolder.parentView.setOnClickListener { itemClickListener.listItemClicked(newsArticle) }

    }

}