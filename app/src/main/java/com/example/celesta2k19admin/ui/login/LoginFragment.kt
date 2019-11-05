package com.example.celesta2k19admin.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.celesta2k19admin.Constants.Constants
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.Utils.Utils
import com.example.celesta2k19admin.api.LoginResponse
import com.example.celesta2k19admin.api.RetrofitApi
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    lateinit var preferences: SharedPreferences
    lateinit var button_login: Button
    lateinit var progressBar: View
    lateinit var loginEmail: EditText
    lateinit var loginPassword: EditText
    private var logoutButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)
        button_login = rootView.findViewById(R.id.button_login)
        loginEmail = rootView.findViewById(R.id.editext_login_email)
        loginPassword = rootView.findViewById(R.id.editext_login_password)
        progressBar = rootView.findViewById(R.id.layout_progress)
        preferences = context!!.getSharedPreferences(Constants.PREF_FILENAME, 0)
        if (!preferences.getBoolean("login_status", false))
            button_login.text = "Tap to login"
        else {
            //logged in
            button_login.isEnabled = false
            val loginRoot = rootView.findViewById<View>(R.id.login_root)
            loginRoot.visibility = View.GONE
            logoutButton = rootView.findViewById(R.id.logout_button)
            logoutButton?.visibility = View.VISIBLE
            val loggedinText = rootView.findViewById<TextView>(R.id.loggedin_text)
            loggedinText.visibility = View.VISIBLE
            val logintext = "Position: ${preferences.getString("position","")}\nPermit: ${preferences.getString("permit","")}"
            loggedinText.text=logintext
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_login.setOnClickListener {
            if (!Utils.isNetworkConnected(context))
                Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show()
            else if (preferences.getBoolean("login_status", false))
                Toast.makeText(context, "You are already logged in", Toast.LENGTH_SHORT).show()
            else
                login()
        }

        logoutButton?.setOnClickListener { view ->
            resetDatas()
        }
    }

    private fun login() {
        progressBar.visibility = View.VISIBLE

        val emailStr = loginEmail.text.toString().trim()
        val passwordStr = loginPassword.text.toString().trim()

        val retofitApi = RetrofitApi.create()
        val email = RequestBody.create(MediaType.parse("text/plain"), emailStr)
        val password = RequestBody.create(MediaType.parse("text/plain"), passwordStr)

        val call = retofitApi.login(email, password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressBar.visibility = View.INVISIBLE
                val status = response.body()?.status()
                if (status == 200) {
                    Toast.makeText(context, "Successfully Logged in", Toast.LENGTH_SHORT).show()
                    storeDatas(
                        response.body()?.access_token(),
                        response.body()?.permit(),
                        response.body()?.position(),
                        response.body()?.email()
                    )
                    findNavController().navigate(R.id.nav_login, null)
                } else if (status == 204)
                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                else if (status == 405)
                    Toast.makeText(context, "Method not found", Toast.LENGTH_SHORT).show()
                else if (status == 401)
                    Toast.makeText(context, "Incorrect Password", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun storeDatas(
        access_token: String?,
        permit: String?,
        position: String?,
        email: String?
    ) {
        val editor = preferences.edit()
        editor.putBoolean("login_status", true)
        editor.putString("access_token", access_token)
        editor.putString("permit", permit)
        editor.putString("position", position)
        editor.putString("email", email)
        editor.apply()
    }

    private fun resetDatas() {
        val editor = preferences.edit()
        editor.putBoolean("login_status", false)
        editor.putString("access_token", "")
        editor.putString("permit", "")
        editor.putString("position", "")
        editor.putString("email", "")
        editor.apply()
        Toast.makeText(context, "Successfully Logged out", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.nav_login, null)
    }


}