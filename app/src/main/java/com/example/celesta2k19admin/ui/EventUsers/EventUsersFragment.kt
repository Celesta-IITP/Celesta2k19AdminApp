package com.example.celesta2k19admin.ui.EventUsers

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.celesta2k19admin.Constants.Constants
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.Utils.Utils
import com.example.celesta2k19admin.api.EventUsersResponse
import com.example.celesta2k19admin.api.RetrofitApi
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class EventUsersFragment : Fragment() {

    private lateinit var eventUsersViewModel: EventUsersViewModel
    lateinit var preferences: SharedPreferences
    private var progressDialog: ProgressDialog? = null
    private lateinit var eventUsersRecyclerView: RecyclerView
    private lateinit var eventUsersAdapter: EventUsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = requireContext().getSharedPreferences(Constants.PREF_FILENAME, 0)
        eventUsersViewModel =
            ViewModelProviders.of(this).get(EventUsersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_event_users, container, false)
        val textView: TextView = root.findViewById(R.id.text_share)
        eventUsersViewModel.text.observe(this, Observer {
            textView.text = it
        })
        eventUsersRecyclerView = root.findViewById(R.id.event_user_recycler_view)
        eventUsersAdapter = EventUsersAdapter()
        eventUsersRecyclerView.layoutManager = LinearLayoutManager(context)
        eventUsersRecyclerView.adapter = eventUsersAdapter
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getEventDetails()
    }

    fun getEventDetails() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Getting Information...")
        progressDialog?.show()

        val retrofitApi = RetrofitApi.create()

        val access_token = RequestBody.create(
            MediaType.parse("text/plain"),
            preferences.getString("access_token", "")
        )
        val permit =
            RequestBody.create(MediaType.parse("text/plain"), preferences.getString("permit", ""))
        val email =
            RequestBody.create(MediaType.parse("text/plain"), preferences.getString("email", ""))
        val evid = arguments?.getString("ev_id").toString()
        val ev_id =
            RequestBody.create(MediaType.parse("text/plain"), evid)
        val is_team_event = arguments?.getString("is_team_event")

        val call = retrofitApi.getEventRegisteredUsers(access_token, permit, email, ev_id)
        call.enqueue(object : Callback<EventUsersResponse> {
            override fun onFailure(call: Call<EventUsersResponse>, t: Throwable) {
                progressDialog?.dismiss()
                Log.e("error:", t.message)
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<EventUsersResponse>,
                response: Response<EventUsersResponse>
            ) {
                progressDialog?.dismiss()
                val status = response.body()?.getStatus()
                if (status == 401)
                    Utils.simpleDialog(context, "Error", response.body()?.getmessage().toString())
                val is_team_event = response.body()?.isTeamEvent()
                if (is_team_event == 0) {
                    //registered users
                    val regsiteredUsers = response.body()?.getRegisteredUsers()
                    Log.e("data:", regsiteredUsers.toString())
                    if (regsiteredUsers != null) {
                        eventUsersAdapter.setUserList(regsiteredUsers)
                    }
                    eventUsersAdapter.setIsTeamEvent(is_team_event)

                } else if (is_team_event == 1) {
                    //team event
                    val teamDetails = response.body()?.getTeamDetails()
                    Log.e("data: ", teamDetails.toString())
                    eventUsersAdapter.setTeamUserList(teamDetails!!)
                    eventUsersAdapter.setIsTeamEvent(is_team_event)
                } else
                    Toast.makeText(context, "No value returned", Toast.LENGTH_SHORT).show()
            }
        })
    }
}