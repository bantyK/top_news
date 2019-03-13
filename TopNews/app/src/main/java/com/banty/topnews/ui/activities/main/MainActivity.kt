package com.banty.topnews.ui.activities.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.banty.topnews.R
import com.banty.topnews.ui.activities.constants.Constants
import com.banty.topnews.ui.activities.news.NewsActivity
import com.banty.topnews.ui.fragments.CountryChoiceFragment
import com.banty.topnews.ui.presenter.MainActivityPresenter
import com.banty.topnews.ui.presenter.impl.MainActivityPresenterImpl


private val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainActivityPresenter.View, CountrySelectionListener {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun showNewsFragment(country: String) {
        saveCountryInSharedPref(country)
        val intent = Intent(this@MainActivity, NewsActivity::class.java)
        intent.putExtra(NewsActivity.INTENT_KEY_COUNTRY_ID, country)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun saveCountryInSharedPref(country: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Constants.SHARED_PREF_KEY_COUNTRY, country)
        editor.apply()
    }

    private fun getStoredCountry(): String {
        return sharedPreferences.getString(Constants.SHARED_PREF_KEY_COUNTRY, "")!!
    }

    override fun countrySelected(country: String) {
        Log.d(TAG, "Selected Country : $country")
        mainActivityPresenter.countryReceived(country)
    }

    override fun showCountryChoiceFragment() {
        val countryChoiceFragment = CountryChoiceFragment()

        if (getStoredCountry().isEmpty())
            showCountryChoiceFragment(countryChoiceFragment)
        else
            showNewsFragment(getStoredCountry())
    }

    private fun showCountryChoiceFragment(countryChoiceFragment: CountryChoiceFragment) {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, countryChoiceFragment)
            .commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(packageName.plus(Constants.SHARED_PREF_NAME), Context.MODE_PRIVATE)
        mainActivityPresenter = MainActivityPresenterImpl(this)
    }


    override fun onResume() {
        super.onResume()
        mainActivityPresenter.resume()
    }


}
