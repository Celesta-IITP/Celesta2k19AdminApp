package `in`.org.celesta.admin

import `in`.org.celesta.admin.Constants.Constants
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var preferences: SharedPreferences
    private lateinit var navCheckinCheckout: MenuItem
    private lateinit var navAllEvents: MenuItem
    private lateinit var navAccommodation: MenuItem
    private lateinit var navAccommodationUsers: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val menu: Menu = navView.menu
        navAccommodation = menu.findItem(R.id.nav_accommodation)
        navAllEvents = menu.findItem(R.id.nav_all_events)
        navCheckinCheckout = menu.findItem(R.id.nav_checkin_checkout_user)
        navAccommodationUsers = menu.findItem(R.id.nav_accommodation_users)

        preferences = applicationContext.getSharedPreferences(Constants.PREF_FILENAME, 0)
        val navController = findNavController(R.id.nav_host_fragment)
        preferences = this.getSharedPreferences(Constants.PREF_FILENAME, 0)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_login,
                R.id.nav_checkin_checkout_user,
                R.id.nav_all_events,
                R.id.nav_user_details,
                R.id.nav_accommodation,
                R.id.nav_accommodation_users
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        if (preferences.getString("permit", "") == "2") {
            navAllEvents.setVisible(false)
            navCheckinCheckout.setVisible(true)
            navAccommodation.setVisible(true)
            navAccommodationUsers.setVisible(true)
        } else if (preferences.getString("permit", "") == "3") {
            navAllEvents.setVisible(false)
            navCheckinCheckout.setVisible(false)
            navAccommodation.setVisible(false)
            navAccommodationUsers.setVisible(false)
        } else if (preferences.getString("permit", "") == "4") {
            navAccommodation.setVisible(false)
            navCheckinCheckout.setVisible(false)
            navAllEvents.setVisible(true)
            navAccommodationUsers.setVisible(false)
        } else if (preferences.getString("permit", "") == "5") {
            navAllEvents.setVisible(false)
            navCheckinCheckout.setVisible(false)
            navAccommodation.setVisible(true)
            navAccommodationUsers.setVisible(true)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds userList to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val login_status = preferences.getBoolean("login_status", false)
        if (login_status == false) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home, null)
        } else {
            super.onBackPressed()
        }
    }
}
