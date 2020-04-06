package `in`.org.celesta.admin.api

import androidx.annotation.Keep

@Keep
class AccommodationUsers(
    var celestaid: String = "",
    var names: String = "",
    var phone: String = "",
    var amount_paid: Float = 0.00f,
    var gender: String = ""
)