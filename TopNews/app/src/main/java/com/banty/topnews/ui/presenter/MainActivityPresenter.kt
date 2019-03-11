package com.banty.topnews.ui.activities.main

import com.banty.topnews.ui.presenter.BasePresenter

/**
 * Created by Banty on 10/03/19.
 */
interface MainActivityPresenter : BasePresenter {

    interface View {
        fun showCountryChoiceFragment()
    }
}