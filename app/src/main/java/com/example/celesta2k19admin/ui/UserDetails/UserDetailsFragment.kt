package com.example.celesta2k19admin.ui.UserDetails


import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.celesta2k19admin.Constants.Constants

import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.Utils.Utils
import com.example.celesta2k19admin.api.RetrofitApi
import com.example.celesta2k19admin.api.UserDetailsResponse
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsFragment : Fragment() {
    private lateinit var UserDetailButton: Button
    lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preferences = context!!.getSharedPreferences(Constants.PREF_FILENAME, 0)
        if (!preferences.getBoolean("login_status", false)) {
            Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.nav_login, null)
        }
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_user_details, container, false)
        UserDetailButton = rootView.findViewById(R.id.button_scan_qr_user_details)

        if (arguments?.getString("celestaid") != null) {
            val celestaid: String = arguments?.getString("celestaid").toString()
            Toast.makeText(context, "data: " + celestaid, Toast.LENGTH_SHORT).show()
//            getUserDetails(celestaid)
            var bundle = bundleOf("celestaid" to celestaid)
            findNavController().navigate(R.id.nav_user_details_output, bundle)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UserDetailButton.setOnClickListener { v ->
            if (!Utils.isNetworkConnected(context))
                Utils.simpleDialog(context, "Error", "Check Your Network Connection")
            else {
                val bundle = bundleOf("fragment_source" to "user_details")
                findNavController().navigate(R.id.nav_scanQr, bundle)
            }
        }
    }


}
