package com.example.celesta2k19admin.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    fun isNetworkConnected(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo != null
    }

    fun showSoftKeyboard(context: Context?, view: View) {
        view.requestFocus()
        val manager = context?.getSystemService(Context.INPUT_METHOD_SERVICE)
        if (manager is InputMethodManager) manager.toggleSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN, InputMethodManager.RESULT_UNCHANGED_HIDDEN
        )
    }

    fun hideSoftKeyboard(context: Context?, view: View) {
        val inputManager: InputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED)
    }
}