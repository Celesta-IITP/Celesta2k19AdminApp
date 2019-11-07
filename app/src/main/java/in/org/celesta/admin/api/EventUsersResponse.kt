package `in`.org.celesta.admin.api

import androidx.annotation.Keep

@Keep
data class EventUsersResponse(
    private val status: Int = 0,
    private val message: String = "",
    private val is_team_event: Int = -1,
    private val registered_users: List<RegisteredUsers> = listOf(),
    private val team_details: List<TeamDetails> = listOf()
) {
    fun getStatus(): Int {
        return status
    }

    fun getmessage(): String {
        return message
    }

    fun isTeamEvent(): Int {
        return is_team_event
    }

    fun getRegisteredUsers(): List<RegisteredUsers> {
        return registered_users
    }

    fun getTeamDetails(): List<TeamDetails> {
        return team_details
    }
}