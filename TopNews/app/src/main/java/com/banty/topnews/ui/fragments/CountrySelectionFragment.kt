package com.banty.topnews.ui.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.banty.topnews.R
import com.banty.topnews.ui.activities.main.CountrySelectionListener
import com.banty.topnews.ui.activities.main.MainActivity
import com.banty.topnews.ui.presenter.CountrySelectionPresenter
import com.banty.topnews.ui.presenter.impl.CountrySelectionPresenterImpl


private const val TAG = "CountryChoiceFragment"

class CountryChoiceFragment : Fragment(), CountrySelectionPresenter.View {


    private lateinit var listener: CountrySelectionListener

    private lateinit var countrySpinner: Spinner

    private lateinit var countrySelectionPresenter: CountrySelectionPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is MainActivity) {
            throw IllegalStateException("Context should be of MainActivity")
        } else {
            listener = context
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_country_choice, container, false)
        countrySelectionPresenter = CountrySelectionPresenterImpl(this)
        setCountrySpinner(view)
        return view
    }

    private fun setCountrySpinner(view: View) {
        countrySpinner = view.findViewById(R.id.country_spinner)

        ArrayAdapter.createFromResource(context, R.array.country_names, android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                countrySpinner.adapter = it
            }
        countrySpinner.post {
            countrySpinner.onItemSelectedListener = CountrySpinnerItemSelectedListener()
        }

    }

    /**
     * Sends a callback to the parent activity when user selects a country from the drop down spinner.
     * Sends the country code instead of the country name.
     * */
    override fun countrySelected(position: Int) {
        val stringArray = resources.getStringArray(R.array.country_codes)
        listener.countrySelected(stringArray[position])
    }

    /**
     * Item click listener for the spinner
     * */
    inner class CountrySpinnerItemSelectedListener : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            Log.d(TAG, "Country selected : ${resources.getStringArray(R.array.country_names)[position]}")
            countrySelectionPresenter.handleSpinnerCountrySelection(position)
        }

        override fun onNothingSelected(arg0: AdapterView<*>) {
            // not required to be implemented
        }

    }

}
