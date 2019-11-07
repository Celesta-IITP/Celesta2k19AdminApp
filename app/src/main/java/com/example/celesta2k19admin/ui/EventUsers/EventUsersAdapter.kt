package com.example.celesta2k19admin.ui.EventUsers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.celesta2k19admin.R
import com.example.celesta2k19admin.api.RegisteredUsers
import com.example.celesta2k19admin.api.TeamDetails

class EventUsersAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userList: List<RegisteredUsers> = listOf()
    private var teamUserList: List<TeamDetails> = listOf()
    private var is_team_event: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder

        if (viewType == 0) {
            //registered users
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.registered_users_item, parent, false)
            viewHolder = RegisteredUsersViewHolder(view)
        } else {
            //team event
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.team_details_item, parent, false)
            viewHolder = TeamDetailsViewHolder(view)
        }
        return viewHolder
    }

    fun setUserList(registeredUsersList: List<RegisteredUsers>) {
        userList = registeredUsersList
        notifyDataSetChanged()
    }

    fun setTeamUserList(teamDetailsList: List<TeamDetails>) {
        teamUserList = teamDetailsList
        notifyDataSetChanged()
    }

    fun setIsTeamEvent(value: Int) {
        is_team_event = value
    }

    override fun getItemViewType(position: Int): Int {
        if (is_team_event == 0) return 0
        else if (is_team_event == 1) return 1
        return -1
    }

    override fun getItemCount(): Int {
        if (is_team_event == 0)
            return this.userList.size
        else
            return this.teamUserList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (is_team_event == 0) {
            //registered users
            val vh1: RegisteredUsersViewHolder = holder as RegisteredUsersViewHolder
            configureRegisteredUsersViewHolder(vh1, position)
            return
        } else if (is_team_event == 1) {
            //team event
            val vh2: TeamDetailsViewHolder = holder as TeamDetailsViewHolder
            configureTeamDetailsViewHolder(vh2, position)
            return
        }
    }

    fun configureRegisteredUsersViewHolder(vh1: RegisteredUsersViewHolder, position: Int) {
        val registeredUsers: RegisteredUsers = userList.get(position) as RegisteredUsers
        if (registeredUsers != null) {
            if (registeredUsers.name == null || registeredUsers.name == "") {
                vh1.name.visibility = View.GONE
            } else {
                vh1.name.visibility = View.VISIBLE
                vh1.name.text = "Name: ${registeredUsers.name}"
            }
            if (registeredUsers.celestaid == null || registeredUsers.celestaid == "") {
                vh1.celestaid.visibility = View.GONE
            } else {
                vh1.celestaid.visibility = View.VISIBLE
                vh1.celestaid.text = "Celesta ID: ${registeredUsers.celestaid}"
            }
            if (registeredUsers.amount.toString() == "") {
                vh1.amount.visibility = View.GONE
            } else {
                vh1.amount.visibility = View.VISIBLE
                vh1.amount.text = "Amount: ${registeredUsers.amount.toString()}"
            }
            if (registeredUsers.phone == "" || registeredUsers.phone == null) {
                vh1.phone.visibility = View.GONE
            } else {
                vh1.phone.visibility = View.VISIBLE
                vh1.phone.text = "Phone: ${registeredUsers.phone}"
            }
        }
    }

    fun configureTeamDetailsViewHolder(vh2: TeamDetailsViewHolder, position: Int) {
        val teamDetails: TeamDetails = teamUserList.get(position)
        if (teamDetails != null) {
            if (teamDetails.team_name != null) {
                Log.d("KHANKI", teamDetails.team_name)
            }
            // Correct
            if (teamDetails.team_name.isNotBlank() && teamDetails.team_name != null) {
                vh2.teamName.visibility = View.VISIBLE
                vh2.teamName.text = "Team Name: ${teamDetails.team_name}"
            } else {
                vh2.teamName.visibility = View.GONE
            }
            // Correct
            if (teamDetails.amount.toString().isNotBlank() && teamDetails.amount != null) {
                vh2.amount.visibility = View.VISIBLE
                vh2.amount.text =
                    "Amount: ${teamDetails.amount.toString()}"
            } else {
                vh2.amount.visibility = View.GONE
            }

            // Correct
            if (teamDetails.cap_name != "" && teamDetails.cap_name != null) {
                vh2.capName.visibility = View.VISIBLE
                vh2.capName.text =
                    "Captain Name: ${teamDetails.cap_name}"
            } else {
                vh2.capName.visibility = View.GONE
            }

            if (teamDetails.cap_celestaid != "" && teamDetails.cap_celestaid != null) {
                vh2.capCelestaId.visibility = View.VISIBLE
                vh2.capCelestaId.text =
                    "Captain Celesta ID: ${teamDetails.cap_celestaid}"
            } else {
                vh2.capCelestaId.visibility = View.GONE
            }

            // Correct
            if (teamDetails.cap_phone != "" && teamDetails.cap_phone != null) {
                vh2.capPhone.visibility = View.VISIBLE
                vh2.capPhone.text =
                    "Captain Phone: ${teamDetails.cap_phone}"
            } else {
                vh2.capPhone.visibility = View.GONE
            }

            if (teamDetails.cap_email != "" && teamDetails.cap_email != null) {
                vh2.capEmail.visibility = View.VISIBLE
                vh2.capEmail.text =
                    "Captain Email: ${teamDetails.cap_email}"
            } else {
                vh2.capEmail.visibility = View.GONE
            }

            if (teamDetails.mem1_name != "" && teamDetails.mem1_name != null) {
                vh2.mem1Name.visibility = View.VISIBLE
                vh2.mem1Name.text =
                    "Member 2 name: " + teamDetails.mem1_name
            } else {
                vh2.mem1Name.visibility = View.GONE
            }

            if (teamDetails.mem1_celestaid != "" && teamDetails.mem1_celestaid != null) {
                vh2.mem1CelestaId.visibility = View.VISIBLE
                vh2.mem1CelestaId.text =
                    "Member 2 Celesta ID: " + teamDetails.mem1_celestaid
            } else {
                vh2.mem1CelestaId.visibility = View.GONE
            }

            if (teamDetails.mem1_phone != "" && teamDetails.mem1_phone != null) {
                vh2.mem1Phone.visibility = View.VISIBLE
                vh2.mem1Phone.text =
                    "Member 2 Phone: " + teamDetails.mem1_phone
            } else {
                vh2.mem1Phone.visibility = View.GONE
            }

            if (teamDetails.mem1_email != "" && teamDetails.mem1_email != null) {
                vh2.mem1Email.visibility = View.VISIBLE
                vh2.mem1Email.text =
                    "Member 2 Email: " + teamDetails.mem1_email
            } else {
                vh2.mem1Email.visibility = View.GONE
            }

            if (teamDetails.mem2_name != "" && teamDetails.mem2_name != null) {
                vh2.mem2Name.visibility = View.VISIBLE
                vh2.mem2Name.text =
                    "Member 3 name: " + teamDetails.mem2_name
            } else {
                vh2.mem2Name.visibility = View.GONE
            }

            if (teamDetails.mem2_celestaid != "" && teamDetails.mem2_celestaid != null) {
                vh2.mem2CelestaId.visibility = View.VISIBLE
                vh2.mem2CelestaId.text =
                    "Member 3 Celesta ID: " + teamDetails.mem2_celestaid
            } else {
                vh2.mem2CelestaId.visibility = View.GONE
            }

            if (teamDetails.mem2_phone != "" && teamDetails.mem2_phone != null) {
                vh2.mem2Phone.visibility = View.VISIBLE
                vh2.mem2Phone.text =
                    "Member 3 Phone: " + teamDetails.mem2_phone
            } else {
                vh2.mem2Phone.visibility = View.GONE
            }

            if (teamDetails.mem2_email != "" && teamDetails.mem2_email != null) {
                vh2.mem2Email.visibility = View.VISIBLE
                vh2.mem2Email.text =
                    "Member 3 Email: " + teamDetails.mem2_email
            } else {
                vh2.mem2Email.visibility = View.GONE
            }

            if (teamDetails.mem3_name != "" && teamDetails.mem3_name != null) {
                vh2.mem3Name.visibility = View.VISIBLE
                vh2.mem3Name.text =
                    "Member 4 name: " + teamDetails.mem3_name
            } else {
                vh2.mem3Name.visibility = View.GONE
            }

            if (teamDetails.mem3_celestaid != "" && teamDetails.mem3_celestaid != null) {
                vh2.mem3CelestaId.visibility = View.VISIBLE
                vh2.mem3CelestaId.text =
                    "Member 4 Celesta ID: " + teamDetails.mem3_celestaid
            } else {
                vh2.mem3CelestaId.visibility = View.GONE
            }

            if (teamDetails.mem3_phone != "" && teamDetails.mem3_phone != null) {
                vh2.mem3Phone.visibility = View.VISIBLE
                vh2.mem3Phone.text =
                    "Member 4 Phone: " + teamDetails.mem3_phone
            } else {
                vh2.mem3Phone.visibility = View.GONE
            }

            if (teamDetails.mem3_email != "" && teamDetails.mem3_email != null) {
                vh2.mem3Email.visibility = View.VISIBLE
                vh2.mem3Email.text =
                    "Member 4 Email: " + teamDetails.mem3_email
            } else {
                vh2.mem3Email.visibility = View.GONE
            }

            if (teamDetails.mem4_name != "" && teamDetails.mem4_name != null) {
                vh2.mem4Name.visibility = View.VISIBLE
                vh2.mem4Name.text =
                    "Member 5 name: " + teamDetails.mem4_name
            } else {
                vh2.mem4Name.visibility = View.GONE
            }

            if (teamDetails.mem4_celestaid != "" && teamDetails.mem4_celestaid != null) {
                vh2.mem4CelestaId.visibility = View.VISIBLE
                vh2.mem4CelestaId.text =
                    "Member 5 Celesta ID: " + teamDetails.mem4_celestaid
            } else {
                vh2.mem4CelestaId.visibility = View.GONE
            }

            if (teamDetails.mem4_phone != "" && teamDetails.mem4_phone != null) {
                vh2.mem4Phone.visibility = View.VISIBLE
                vh2.mem4Phone.text =
                    "Member 5 Phone: " + teamDetails.mem4_phone
            } else {
                vh2.mem4Phone.visibility = View.GONE
            }

            if (teamDetails.mem4_email != "" && teamDetails.mem4_email != null) {
                vh2.mem4Email.visibility = View.VISIBLE
                vh2.mem4Email.text =
                    "Member 5 Email: " + teamDetails.mem4_email
            } else {
                vh2.mem4Email.visibility = View.GONE
            }
        }
    }

    class RegisteredUsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.registered_user_name)
        val celestaid: TextView = itemView.findViewById(R.id.registered_user_celestaid)
        val phone: TextView = itemView.findViewById(R.id.registered_user_phone)
        val amount: TextView = itemView.findViewById(R.id.registered_user_amount)
    }

    class TeamDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName: TextView = itemView.findViewById(R.id.team_details_team_name)
        val amount: TextView = itemView.findViewById(R.id.team_details_amount)
        val capName: TextView = itemView.findViewById(R.id.team_details_cap_name)
        val capCelestaId: TextView = itemView.findViewById(R.id.team_details_cap_celestaid)
        val capPhone: TextView = itemView.findViewById(R.id.team_details_cap_phone)
        val capEmail: TextView = itemView.findViewById(R.id.team_details_cap_email)

        val mem1Name: TextView = itemView.findViewById(R.id.team_details_mem1_name)
        val mem1CelestaId: TextView = itemView.findViewById(R.id.team_details_mem1_celestaid)
        val mem1Phone: TextView = itemView.findViewById(R.id.team_details_mem1_phone)
        val mem1Email: TextView = itemView.findViewById(R.id.team_details_mem1_email)

        val mem2Name: TextView = itemView.findViewById(R.id.team_details_mem2_name)
        val mem2CelestaId: TextView = itemView.findViewById(R.id.team_details_mem2_celestaid)
        val mem2Phone: TextView = itemView.findViewById(R.id.team_details_mem2_phone)
        val mem2Email: TextView = itemView.findViewById(R.id.team_details_mem2_email)

        val mem3Name: TextView = itemView.findViewById(R.id.team_details_mem3_name)
        val mem3CelestaId: TextView = itemView.findViewById(R.id.team_details_mem3_celestaid)
        val mem3Phone: TextView = itemView.findViewById(R.id.team_details_mem3_phone)
        val mem3Email: TextView = itemView.findViewById(R.id.team_details_mem3_email)

        val mem4Name: TextView = itemView.findViewById(R.id.team_details_mem4_name)
        val mem4CelestaId: TextView = itemView.findViewById(R.id.team_details_mem4_celestaid)
        val mem4Phone: TextView = itemView.findViewById(R.id.team_details_mem4_phone)
        val mem4Email: TextView = itemView.findViewById(R.id.team_details_mem4_email)
    }
}