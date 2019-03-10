package com.banty.topnews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.banty.topnews.network.retrofit.NewsApiService
import com.banty.topnews.network.retrofit.RetrofitClientCreator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitClientCreator.createRetrofitClient()
            .create(NewsApiService::class.java)
            .getTopHeadlines("in", "94e491f91fd24aa5bc4cc2d1915d849f")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ topHeadlinesResponse ->
                Log.i("Banty", "${topHeadlinesResponse.articles.size}")
            },
                { error ->
                    Log.i("Banty", "Error : ${error.message}")
                })
    }
}
