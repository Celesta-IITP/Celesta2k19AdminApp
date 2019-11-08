package `in`.org.celesta.admin.api

import androidx.annotation.Keep

@Keep
class AccommodationUsersResponse(
    var status: Int = 0,
    var message: String = "",
    var users: List<AccommodationUsers>
)