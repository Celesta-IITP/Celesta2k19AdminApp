package com.example.celesta2k19admin.ui.UserDetails


import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.celesta2k19admin.Constants.Constants

import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.Utils.Utils
import com.example.celesta2k19admin.api.RetrofitApi
import com.example.celesta2k19admin.api.UserDetailsResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsOutputFragment : Fragment() {

    lateinit var preferences: SharedPreferences
    private var progressDialog: ProgressDialog? = null
    private lateinit var UserDetailsRecyclerView: RecyclerView
    private lateinit var userRegisteredEventsAdapter: UserDetailsOutputAdapter
    private lateinit var name: TextView
    private lateinit var emailTextView: TextView
    private lateinit var registrationDesk: TextView
    private lateinit var phone: TextView
    private lateinit var college: TextView
    private lateinit var amountPaid: TextView
    private lateinit var accommodation: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_user_details_output, container, false)
        preferences = requireContext().getSharedPreferences(Constants.PREF_FILENAME, 0)

        name = rootView.findViewById(R.id.user_details_name)
        emailTextView = rootView.findViewById(R.id.user_details_email)
        registrationDesk = rootView.findViewById(R.id.user_details_registration_desk)
        phone = rootView.findViewById(R.id.user_details_phone)
        college = rootView.findViewById(R.id.user_details_college)
        amountPaid = rootView.findViewById(R.id.user_details_amount_paid)
        accommodation = rootView.findViewById(R.id.user_details_accommodation)

        UserDetailsRecyclerView = rootView.findViewById(R.id.user_registered_events_recycler_view)
        userRegisteredEventsAdapter = UserDetailsOutputAdapter()
        UserDetailsRecyclerView.layoutManager = LinearLayoutManager(context)
        UserDetailsRecyclerView.adapter = userRegisteredEventsAdapter

        if (arguments?.getString("celestaid") != null) {
            val celestaid: String = arguments?.getString("celestaid").toString()
            getUserDetails(celestaid)
        }

        return rootView
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
                findNavController().navigate(R.id.nav_user_details)
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
                    val list = response.body()?.events_registered
                    if (list != null)
                        userRegisteredEventsAdapter.setRegisteredEventsList(list)
                    name.text = "Name: " + response.body()?.name
                    emailTextView.text = "Email: " + response.body()?.email
                    if (response.body()?.registration_desk == 0)
                        registrationDesk.text = "Account not verified at registration desk"
                    else
                        registrationDesk.text = "Account verified at registration desk"
                    phone.text = "Phone: " + response.body()?.phone
                    college.text = "College: " + response.body()?.college
                    amountPaid.text = "Amount Paid: " + response.body()?.amount_paid.toString()
                    if (response.body()?.accommodation == 0) accommodation.text =
                        "User has not booked accommodation"
                    else if (response.body()?.accommodation == 1) accommodation.text =
                        "Accommodation booked and paid"
                    else accommodation.text = "Accommodation booked but not paid"
                }
            }
        })
    }
}
