package com.banty.topnews.ui.presenter

/**
 * Created by Banty on 11/03/19.
 */
interface CountrySelectionPresenter : BasePresenter {

    interface View {
        fun countrySelected(position: Int)
    }

    fun handleSpinnerCountrySelection(position: Int)

}