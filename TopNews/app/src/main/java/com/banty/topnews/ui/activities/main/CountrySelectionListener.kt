package com.banty.topnews.ui.activities.main

/**
 * Created by Banty on 10/03/19.
 *
 * Listener to pass the selected country from CountrySelectionFragment to MainActivity.
 * This will allow loose coupling between MainActivity and CountrySelectionFragment
 */
interface CountrySelectionListener {

    fun countrySelected(countryId:String)
}