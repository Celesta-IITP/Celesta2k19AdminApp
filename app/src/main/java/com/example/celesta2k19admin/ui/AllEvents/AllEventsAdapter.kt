package com.example.celesta2k19admin.ui.AllEvents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.api.AllEvents

class AllEventsAdapter(val context: Context?) :
    RecyclerView.Adapter<AllEventsAdapter.ViewHolder>() {
    var allEvents: List<AllEvents> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllEventsAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.all_events_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allEvents.size
    }

    override fun onBindViewHolder(holder: AllEventsAdapter.ViewHolder, position: Int) {
        holder.name.text = "Event Name: " + allEvents.get(position).ev_name
        holder.id.text = "Event ID: " + allEvents.get(position).ev_id
        if (allEvents.get(position).is_team_event == 0)
            holder.team_check.text = "Not Team Event"
        else if (allEvents.get(position).is_team_event == 1)
            holder.team_check.text = "Team Event"
        else
            holder.team_check.text = allEvents.get(position).is_team_event.toString()
        holder.buttonViewAllUsers.setOnClickListener { v ->
            val bundle = bundleOf(
                "ev_id" to allEvents.get(position).ev_id,
                "is_team_event" to allEvents.get(position).is_team_event.toString()
            )
            holder.itemView.findNavController().navigate(R.id.nav_event_users, bundle)
        }
    }

    fun setAllEventsList(allEvents: List<AllEvents>) {
        this.allEvents = allEvents
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.all_events_name)
        val id: TextView = itemView.findViewById(R.id.all_events_id)
        val team_check: TextView = itemView.findViewById(R.id.all_events_team_check)
        val buttonViewAllUsers: Button = itemView.findViewById(R.id.button_registered_users_teams)
    }
}