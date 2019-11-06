package com.example.celesta2k19admin.ui.home

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
import com.example.celesta2k19admin.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeLoginButton: Button
    private lateinit var homeCheckinCheckoutButton: Button
    private lateinit var homeAllEventsButton: Button
    private lateinit var homeUserDetailsButton: Button
    private lateinit var homeAccommodationButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeLoginButton = root.findViewById(R.id.home_login_button)
        homeCheckinCheckoutButton = root.findViewById(R.id.home_checkin_checkout_button)
        homeAllEventsButton = root.findViewById(R.id.home_all_events_button)
        homeUserDetailsButton = root.findViewById(R.id.home_user_details_button)
        homeAccommodationButton = root.findViewById(R.id.home_accommodation_button)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
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