package com.banty.topnews.ui.presenter.impl

import android.util.Log
import com.banty.topnews.ui.presenter.MainActivityPresenter

/**
 * Created by Banty on 10/03/19.
 */
private val TAG = "MainActivityPresenter"

class MainActivityPresenterImpl constructor(private val view: MainActivityPresenter.View) : MainActivityPresenter {
    override fun countryReceived(country: String) {
        view.showNewsFragment(country)
    }

    override fun resume() {
        view.showCountryChoiceFragment()
    }

    override fun pause() {
        // not required to be implemented
    }

    override fun stop() {
        // not required to be implemented
    }

    override fun destroy() {
        // not required to be implemented
    }
}