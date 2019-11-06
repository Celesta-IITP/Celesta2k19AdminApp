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
    private var progressDialog: ProgressDialog? = null

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
            getUserDetails(celestaid)
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

    fun getUserDetails(celestaId: String) {
        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Processing...")
        progressDialog?.show()

        val retrofitApi = RetrofitApi.create()

        val access_token = RequestBody.create(
            MediaType.parse("text/plain"),
            preferences.getString("access_token", "")
        )
        val permit =
            RequestBody.create(MediaType.parse("text/plain"), preferences.getString("permit", ""))
        val celestaid = RequestBody.create(MediaType.parse("text/plain"), celestaId)
        val email =
            RequestBody.create(MediaType.parse("text/plain"), preferences.getString("email", ""))

        val call = retrofitApi.getUserDetails(access_token, permit, email, celestaid)

        call.enqueue(object : Callback<UserDetailsResponse> {
            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.e("Error: ", t.message)
            }

            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                progressDialog?.dismiss()
                val status = response.body()?.status
                if (status != 200)
                    Utils.simpleDialog(context, "Error", response.body()?.message.toString())
                else {
                    Utils.simpleDialog(context, "Success", response.body().toString())
                }
            }
        })
    }
}
