package com.banty.topnews.ui.activities.news

import android.util.Log
import com.banty.topnews.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Banty on 12/03/19.
 */
class NewsActivityPresenterImpl(
    private val view: NewsActivityPresenter.View,
    private val newsRepository: NewsRepository
) : NewsActivityPresenter {

    val TAG = "NewsActivityPresenter"

    override fun resume() {
        newsRepository.getNewsHeadlines("in", "sports")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.articles?.apply {
                    Log.d(TAG, "News articles size : ${response.articles.size}")
                    view.setRecyclerView(response.articles)
                }
            }, { error ->
                Log.d(TAG, "Error : ${error.message}")
            })

    }

    override fun pause() {

    }

    override fun stop() {

    }

    override fun destroy() {

    }
}