package com.example.celesta2k19admin.api

data class UserDetailsResponse(
    var status: Int = 0,
    var message: String = "",
    var accommodation: Int = -1,
    var name: String = "",
    var email: String = "",
    var amount_paid: Float = 0.00f,
    var college: String = "",
    var phone: String = "",
    var registration_desk: Int = -1,
    var events_registered: List<RegisteredEventsItems>
)