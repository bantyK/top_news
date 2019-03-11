package com.banty.topnews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.banty.topnews.di.component.DaggerAppComponent
import com.banty.topnews.di.module.AppModule
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.ui.fragments.CountryChoiceFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .injectDependencies(this)

        val countryChoiceFragment = CountryChoiceFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, countryChoiceFragment)
            .commit()
    }

    override fun onResume() {
        super.onResume()
/*
        newsRepository.getNewsHeadlines("", "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                response.articles?.apply {
                    Log.d("Banty", "News articles size : ${response.articles.size}")
                    // set the recycler view here
                }
            }, { error ->
                Log.d("Banty", "Error : ${error.message}")
            })
*/
    }
}
