package com.banty.topnews.ui.presenter.impl

import com.banty.topnews.ui.presenter.CountrySelectionPresenter

class CountrySelectionPresenterImpl(private val view: CountrySelectionPresenter.View) :
    CountrySelectionPresenter {

    override fun handleSpinnerCountrySelection(position: Int) {
        view.countrySelected(position)
    }

    override fun resume() {
        // implementation not required
    }

    override fun pause() {
        // implementation not required
    }

    override fun stop() {
        // implementation not required
    }

    override fun destroy() {
        // implementation not required
    }

}
