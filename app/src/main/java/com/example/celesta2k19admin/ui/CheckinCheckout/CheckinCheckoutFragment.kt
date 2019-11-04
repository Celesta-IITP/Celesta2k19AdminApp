package com.example.celesta2k19admin.ui.CheckinCheckout

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

class CheckinCheckoutFragment : Fragment() {

    private lateinit var aCheckinCheckoutViewModel: CheckinCheckoutViewModel
    private var scanQrUserButton: Button? = null
    private var textView: TextView? = null
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

        aCheckinCheckoutViewModel =
            ViewModelProviders.of(this).get(CheckinCheckoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_checkin_checkout_user, container, false)
        textView = root.findViewById(R.id.text_slideshow)
        scanQrUserButton = root.findViewById(R.id.scan_user_qr_button)
        aCheckinCheckoutViewModel.text.observe(this, Observer {
            textView?.text = it
        })
        if (arguments?.getString("celestaid") != null) {
            val celestaid: String = arguments?.getString("celestaid").toString()
            Toast.makeText(context, "data: " + celestaid, Toast.LENGTH_SHORT).show()
            checkUser(celestaid)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanQrUserButton?.setOnClickListener { v ->
            if (!Utils.isNetworkConnected(context)) {
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = bundleOf("fragment_source" to "checkin-checkout")
                findNavController().navigate(R.id.nav_scanQr, bundle)
            }
        }
    }

    private fun checkUser(celestaId: String) {
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
        val email =
            RequestBody.create(MediaType.parse("text/plain"), preferences.getString("email", ""))

        val call = retrofitApi.checkUser(access_token, permit, celestaid, date_time, email)
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
                if (status == "200") {
                    //Toast.makeText(context, response.body()?.action(), Toast.LENGTH_SHORT).show()
                    Utils.simpleDialog(
                        context,
                        "Scan Result",
                        "Celesta Id: $celestaId\nStatus: ${response.body()?.action()}"
                    )
                } else if (status == "203") {
                    Toast.makeText(
                        context,
                        "Account has not been verified at registration desk",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (status == "401") {
                    Toast.makeText(
                        context,
                        "Admin unauthorized to perform this action",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (status == "404") {
                    Toast.makeText(context, "Celesta ID not found", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(context, "Invalid status", Toast.LENGTH_SHORT).show()
            }
        })

    }
}