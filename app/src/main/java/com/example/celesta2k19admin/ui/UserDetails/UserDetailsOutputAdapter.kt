package com.example.celesta2k19admin.ui.UserDetails

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.api.RegisteredEventsItems

class UserDetailsOutputAdapter : RecyclerView.Adapter<UserDetailsOutputAdapter.ViewHolder>() {
    private var registeredEventsList: List<RegisteredEventsItems> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserDetailsOutputAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.user_registered_events_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return registeredEventsList.size
    }

    override fun onBindViewHolder(holder: UserDetailsOutputAdapter.ViewHolder, position: Int) {
        holder.ev_id.text = "Event ID: " + registeredEventsList.get(position).ev_id
        holder.amount.text = "Amount: " + registeredEventsList.get(position).amount
        holder.ev_name.text = "Event Name: " + registeredEventsList.get(position).ev_name
        if (!registeredEventsList.get(position).cap_name.isNullOrEmpty()) {
            holder.cap_name.visibility = View.VISIBLE
            holder.cap_name.text = "Captain Name: " + registeredEventsList.get(position).cap_name
        }
        if (!registeredEventsList.get(position).team_name.isNullOrEmpty()) {
            holder.team_name.visibility = View.VISIBLE
            holder.team_name.text = "Team Name: " + registeredEventsList.get(position).team_name
        }
    }

    fun setRegisteredEventsList(list: List<RegisteredEventsItems>) {
        this.registeredEventsList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ev_id: TextView = itemView.findViewById(R.id.user_registered_events_ev_id)
        val amount: TextView = itemView.findViewById(R.id.user_registered_events_amount)
        val ev_name: TextView = itemView.findViewById(R.id.user_registered_events_ev_name)
        val cap_name: TextView = itemView.findViewById(R.id.user_registered_events_cap_name)
        val team_name: TextView = itemView.findViewById(R.id.user_registered_events_team_name)
    }
}