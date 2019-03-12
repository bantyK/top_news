package com.banty.topnews.ui.activities.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.banty.topnews.R
import com.banty.topnews.di.component.DaggerMainActivityComponent
import com.banty.topnews.di.module.PresenterModule
import com.banty.topnews.di.module.RepositoryModule
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.ui.fragments.CountryChoiceFragment
import com.banty.topnews.ui.fragments.NewsFragment
import com.banty.topnews.ui.presenter.MainActivityPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

private val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainActivityPresenter.View, CountrySelectionListener {

    override fun showNewsFragment(country: String) {
        val newsFragment = NewsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newsFragment)
            .commit()
    }

    override fun countrySelected(country: String) {
        Log.d(TAG, "Selected Country : $country")
        mainActivityPresenter.countryReceived(country)
    }

    override fun showCountryChoiceFragment() {
        val countryChoiceFragment = CountryChoiceFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, countryChoiceFragment)
            .commit()
    }

    @Inject
    lateinit var newsRepository: NewsRepository

    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainActivityComponent
            .builder()
            .repositoryModule(RepositoryModule(this.applicationContext))
            .presenterModule(PresenterModule(this))
            .build()
            .injectMainActivityDependencies(this)
    }

    override fun onResume() {
        super.onResume()

        mainActivityPresenter.resume()

        newsRepository.getNewsHeadlines("", "sports")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response.articles?.apply {
                    Log.d("Banty", "News articles size : ${response.articles.size}")
                    // set the recycler view here
                }
            }, { error ->
                Log.d("Banty", "Error : ${error.message}")
            })

    }
}
