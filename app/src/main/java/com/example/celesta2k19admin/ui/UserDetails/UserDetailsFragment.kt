package com.example.celesta2k19admin.ui.UserDetails


import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.celesta2k19admin.Constants.Constants
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.Utils.Utils

class UserDetailsFragment : Fragment() {
    private lateinit var UserDetailButton: Button
    lateinit var preferences: SharedPreferences

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
