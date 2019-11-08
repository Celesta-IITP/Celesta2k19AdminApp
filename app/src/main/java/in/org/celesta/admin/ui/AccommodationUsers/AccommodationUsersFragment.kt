package `in`.org.celesta.admin.ui.AccommodationUsers


import `in`.org.celesta.admin.Constants.Constants
import `in`.org.celesta.admin.R
import `in`.org.celesta.admin.Utils.Utils
import `in`.org.celesta.admin.api.AccommodationUsersResponse
import `in`.org.celesta.admin.api.RetrofitApi
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccommodationUsersFragment : Fragment() {

    private lateinit var accommodationUsersAdapter: AccommodationUsersAdapter
    private lateinit var accommodationUsersRecycler: RecyclerView
    private var progressDialog: ProgressDialog? = null
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        preferences = requireContext().getSharedPreferences(Constants.PREF_FILENAME, 0)
        if (!preferences.getBoolean("login_status", false)) {
            Toast.makeText(context, "You need to login first", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.nav_login, null)
        }

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_accommodation_users, container, false)
        accommodationUsersRecycler = rootView.findViewById(R.id.accommodation_users_recycler_view)
        accommodationUsersAdapter = AccommodationUsersAdapter()
        accommodationUsersRecycler.layoutManager = LinearLayoutManager(context)
        accommodationUsersRecycler.adapter = accommodationUsersAdapter

        if (!Utils.isNetworkConnected(context)) {
            Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.nav_home, null)
        } else if (preferences.getBoolean("login_status", false))
            accommodationUsers()

        return rootView
    }

    fun accommodationUsers() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Getting list of all users...")
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

        val call = retrofitApi.getAccommodationUsers(access_token, permit, email)

        call.enqueue(object : Callback<AccommodationUsersResponse> {
            override fun onFailure(call: Call<AccommodationUsersResponse>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.e("Error:", t.localizedMessage)
                findNavController().navigate(R.id.nav_home, null)
            }

            override fun onResponse(
                call: Call<AccommodationUsersResponse>,
                response: Response<AccommodationUsersResponse>
            ) {
                progressDialog?.dismiss()
                val status = response.body()?.status
                val message = response.body()?.message
                if (status != 200) {
                    Utils.simpleDialog(context, "Error", message.toString())
                    findNavController().navigate(R.id.nav_home, null)
                } else {
                    val userlist = response.body()?.users
                    if (userlist != null)
                        accommodationUsersAdapter.setAccommodationUsersList(userlist)
                }
            }
        })
    }
}
