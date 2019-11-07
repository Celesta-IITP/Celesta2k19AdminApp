package com.example.celesta2k19admin.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.celesta2k19admin.Constants.Constants
import com.example.celesta2k19admin.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeLoginButton: Button
    private lateinit var homeCheckinCheckoutButton: Button
    private lateinit var homeAllEventsButton: Button
    private lateinit var homeUserDetailsButton: Button
    private lateinit var homeAccommodationButton: Button
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        preferences = requireContext().getSharedPreferences(Constants.PREF_FILENAME, 0)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeLoginButton = root.findViewById(R.id.home_login_button)
        homeUserDetailsButton = root.findViewById(R.id.home_user_details_button)

        homeCheckinCheckoutButton = root.findViewById(R.id.home_checkin_checkout_button)
        homeAllEventsButton = root.findViewById(R.id.home_all_events_button)
        homeAccommodationButton = root.findViewById(R.id.home_accommodation_button)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val permit = preferences.getString("permit", "")
        if (permit == "2") {
            homeCheckinCheckoutButton.visibility = View.VISIBLE
            homeAccommodationButton.visibility = View.VISIBLE
            homeUserDetailsButton.visibility = View.VISIBLE
            homeAllEventsButton.visibility = View.GONE
        } else if (permit == "3") {
            homeCheckinCheckoutButton.visibility = View.GONE
            homeAccommodationButton.visibility = View.GONE
            homeUserDetailsButton.visibility = View.VISIBLE
            homeAllEventsButton.visibility = View.GONE
        }
        if (permit == "4") {
            homeCheckinCheckoutButton.visibility = View.GONE
            homeAccommodationButton.visibility = View.GONE
            homeUserDetailsButton.visibility = View.VISIBLE
            homeAllEventsButton.visibility = View.VISIBLE
        }
        if (permit == "5") {
            homeCheckinCheckoutButton.visibility = View.GONE
            homeAccommodationButton.visibility = View.VISIBLE
            homeUserDetailsButton.visibility = View.VISIBLE
            homeAllEventsButton.visibility = View.GONE
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeLoginButton.setOnClickListener { view ->
            findNavController().navigate(R.id.nav_login, null)
        }
        homeCheckinCheckoutButton.setOnClickListener { view ->
            findNavController().navigate(R.id.nav_checkin_checkout_user, null)
        }
        homeAllEventsButton.setOnClickListener { view ->
            findNavController().navigate(R.id.nav_all_events, null)
        }
        homeUserDetailsButton.setOnClickListener { view ->
            findNavController().navigate(R.id.nav_user_details, null)
        }
        homeAccommodationButton.setOnClickListener { view ->
            findNavController().navigate(R.id.nav_accommodation, null)
        }
    }
}