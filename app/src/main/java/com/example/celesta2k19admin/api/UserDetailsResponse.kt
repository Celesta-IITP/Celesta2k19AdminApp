package com.example.celesta2k19admin.api

data class UserDetailsResponse(
    var status: Int = 0,
    var message: String = "",
    var accommodation: Int = -1,
    var iit_patna: Int = -1,
    var amount_paid: Float = 0.00f,
    var college: String = "",
    var phone: String = "",
    var registration_desk: Int = -1
)