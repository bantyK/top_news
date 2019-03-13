package com.banty.topnews.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.banty.topnews.datamodels.Article

/**
 * Created by Banty on 13/03/19.
 */
class HeadlinesViewModel : ViewModel() {

    private var headlineViewModel : MutableLiveData<List<Article>> = MutableLiveData()

    fun setHeadlinesViewModel(articles: List<Article>) {
        headlineViewModel.value = articles
    }

    fun getHeadlinesViewModel() = headlineViewModel

}