package com.banty.topnews.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.banty.topnews.R

private const val ARG_PARAM_COUNTRY_ID = "country.id"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NewsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        (view.findViewById<TextView>(R.id.text_view)).text = arguments?.getString(ARG_PARAM_COUNTRY_ID)
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param countryId : Id of the Country selected by user.
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(countryId: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_COUNTRY_ID, countryId)
                }
            }
    }
}
