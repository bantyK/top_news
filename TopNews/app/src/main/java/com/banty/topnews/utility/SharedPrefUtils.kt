package com.banty.topnews.utility

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Banty on 14/03/19.
 */
class SharedPrefUtils {
    companion object {
        @JvmStatic
        fun getPref(context: Context, key: String, value: Boolean): Boolean {
            val sharedPreferences = getPreferences(context)
            return sharedPreferences.getBoolean(key, value)
        }

        @JvmStatic
        fun getPref(context: Context, key: String, value: String): String {
            val sharedPreferences = getPreferences(context)
            return sharedPreferences.getString(key, value)
        }

        @JvmStatic
        fun putPref(context: Context, key: String, value: Boolean) {
            val sharedPreferences = getPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        @JvmStatic
        fun putPref(context: Context, key: String, value: String) {
            val sharedPreferences = getPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        private fun getPreferences(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

}