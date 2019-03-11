package com.banty.topnews.ui.presenter

/**
 * Created by Banty on 10/03/19.
 */
class MainActivityPresenterImpl constructor(private val view: MainActivityPresenter.View) :
    MainActivityPresenter {

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