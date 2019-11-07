package com.example.celesta2k19admin.api

import androidx.annotation.Keep

@Keep
data class RegisteredEventsItems(
    var ev_id: String = "",
    var amount: Float = 0.00f,
    var ev_name: String = "",
    var cap_name: String = "",
    var team_name: String = ""
)