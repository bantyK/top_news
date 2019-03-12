package com.banty.topnews.ui.presenter


/**
 * Created by Banty on 10/03/19.
 */
interface MainActivityPresenter : BasePresenter {
    fun countryReceived(country: String)

    interface View {
        fun showCountryChoiceFragment()
        fun showNewsFragment(country: String)
    }
}