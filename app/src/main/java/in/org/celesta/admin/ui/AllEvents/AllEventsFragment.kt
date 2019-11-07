package `in`.org.celesta.admin.ui.AllEvents

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import `in`.org.celesta.admin.Constants.Constants
import `in`.org.celesta.admin.R
import `in`.org.celesta.admin.Utils.Utils
import `in`.org.celesta.admin.api.AllEventsResponse
import `in`.org.celesta.admin.api.RetrofitApi
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllEventsFragment : Fragment() {

    private lateinit var allEventsViewModel: AllEventsViewModel
    private lateinit var allEventsAdapter: AllEventsAdapter
    private lateinit var allEventsRecyclerView: RecyclerView
    private var progressDialog: ProgressDialog? = null
    lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = requireContext().getSharedPreferences(Constants.PREF_FILENAME, 0)
        if (!preferences.getBoolean("login_status", false)) {
            Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.nav_login, null)
        }

        allEventsViewModel =
            ViewModelProviders.of(this).get(AllEventsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_events, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)

        allEventsRecyclerView = root.findViewById(R.id.all_events_recycler_view) as RecyclerView
        allEventsAdapter = AllEventsAdapter(context)
        allEventsRecyclerView.layoutManager = LinearLayoutManager(context)
        allEventsRecyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
        allEventsRecyclerView.adapter = allEventsAdapter

        allEventsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        if (!Utils.isNetworkConnected(context))
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
        else if (preferences.getBoolean("login_status", false))
            loadAllEvents()
        return root
    }

    fun loadAllEvents() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Getting list of all events...")
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

        val call = retrofitApi.getAllEvents(access_token, permit, email)

        call.enqueue(object : Callback<AllEventsResponse> {
            override fun onFailure(call: Call<AllEventsResponse>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.e("Error:", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<AllEventsResponse>,
                response: Response<AllEventsResponse>
            ) {
                progressDialog?.dismiss()
                val status = response.body()?.getStatus()
                if (status == 401) {
                    val message = response.body()?.getMessage()
                    Utils.simpleDialog(context, "Error", message.toString())
                } else if (status == 200) {
                    val events = response.body()?.getEvents()
                    if (events != null)
                        allEventsAdapter.setAllEventsList(events)
                } else
                    Toast.makeText(context, "Invalid status code", Toast.LENGTH_SHORT).show()
            }
        })
    }

}