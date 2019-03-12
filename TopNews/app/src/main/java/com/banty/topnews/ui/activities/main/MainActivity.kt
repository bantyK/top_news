package com.banty.topnews.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.banty.topnews.R
import com.banty.topnews.repository.NewsRepository
import com.banty.topnews.ui.activities.news.NewsActivity
import com.banty.topnews.ui.fragments.CountryChoiceFragment
import com.banty.topnews.ui.presenter.MainActivityPresenter
import com.banty.topnews.ui.presenter.impl.MainActivityPresenterImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


private val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainActivityPresenter.View, CountrySelectionListener {

    override fun showNewsFragment(country: String) {
        val intent = Intent(this@MainActivity, NewsActivity::class.java)
        intent.putExtra(NewsActivity.INTENT_KEY_COUNTRY_ID, country)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
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


    lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter = MainActivityPresenterImpl(this)
    }


    override fun onResume() {
        super.onResume()

        mainActivityPresenter.resume()
    }


}
