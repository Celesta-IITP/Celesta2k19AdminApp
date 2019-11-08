package `in`.org.celesta.admin.api

class AccommodationUsersResponse(
    var status: Int = 0,
    var message: String = "",
    var users: List<AccommodationUsers>
)