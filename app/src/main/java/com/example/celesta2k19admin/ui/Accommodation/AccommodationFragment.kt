package com.example.celesta2k19admin.ui.Accommodation

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.celesta2k19admin.Constants.Constants
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.Utils.Utils
import com.example.celesta2k19admin.api.CheckinCheckoutResponse
import com.example.celesta2k19admin.api.RetrofitApi
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AccommodationFragment : Fragment() {

    private lateinit var accommodationViewModel: AccommodationViewModel
    private lateinit var accommodationButton: Button
    lateinit var preferences: SharedPreferences
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preferences = context!!.getSharedPreferences(Constants.PREF_FILENAME, 0)
        if (!preferences.getBoolean("login_status", false)) {
            Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.nav_login, null)
        }

        accommodationViewModel =
            ViewModelProviders.of(this).get(AccommodationViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_accommodation, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_accommodation)
        accommodationButton = rootView.findViewById(R.id.button_accommodation)
        accommodationViewModel.text.observe(this, Observer {
            textView.text = it
        })
        if (arguments?.getString("celestaid") != null) {
            val celestaid: String = arguments?.getString("celestaid").toString()
            Toast.makeText(context, "data: " + celestaid, Toast.LENGTH_SHORT).show()
            checkAccommodation(celestaid)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accommodationButton.setOnClickListener { v ->
            if (!Utils.isNetworkConnected(context))
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
            else {
                val bundle = bundleOf("fragment_source" to "accommodation")
                findNavController().navigate(R.id.nav_scanQr, bundle)
            }
        }
    }

    private fun checkAccommodation(celestaId: String) {
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
        val df = SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss")
        val datetime = df.format(Calendar.getInstance().time)
        val date_time = RequestBody.create(MediaType.parse("text/plain"), datetime)
        Log.e("TAG", "date_time: " + datetime)

        val call = retrofitApi.checkAccommodation(access_token, permit, celestaid, date_time)
        call.enqueue(object : Callback<CheckinCheckoutResponse> {
            override fun onFailure(call: Call<CheckinCheckoutResponse>, t: Throwable) {
                progressDialog?.dismiss()
                Log.e("error:", t.message)
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<CheckinCheckoutResponse>,
                response: Response<CheckinCheckoutResponse>
            ) {
                progressDialog?.dismiss()
                val status = response.body()?.status()
                val message = response.body()?.message()
                var msg = ""
                message?.forEach {
                    msg += it + "\n"
                }
                Utils.simpleDialog(context, "Scan Result", msg)
            }
        })
    }
}