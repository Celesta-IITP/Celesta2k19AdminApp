package `in`.org.celesta.admin.ui.AccommodationUsers

import `in`.org.celesta.admin.R
import `in`.org.celesta.admin.api.AccommodationUsers
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccommodationUsersAdapter : RecyclerView.Adapter<AccommodationUsersAdapter.ViewHolder>() {

    private var userList: List<AccommodationUsers> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccommodationUsersAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.accommodation_users_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setAccommodationUsersList(list: List<AccommodationUsers>) {
        this.userList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AccommodationUsersAdapter.ViewHolder, position: Int) {
        holder.celestaid.text = "Celesta ID: " + userList.get(position).celestaid
        holder.names.text = "Name: " + userList.get(position).names
        holder.phone.text = "Phone: " + userList.get(position).phone
        holder.amountPaid.text = "Amount Paid: " + userList.get(position).amount_paid
        holder.gender.text = "Gender: " + userList.get(position).gender
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val celestaid: TextView = itemView.findViewById(R.id.accommodation_users_celestaid)
        val names: TextView = itemView.findViewById(R.id.accommodation_users_names)
        val phone: TextView = itemView.findViewById(R.id.accommodation_users_phone)
        val amountPaid: TextView = itemView.findViewById(R.id.accommodation_users_amount_paid)
        val gender: TextView = itemView.findViewById(R.id.accommodation_users_gender)
    }
}