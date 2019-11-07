package `in`.org.celesta.admin.api

import androidx.annotation.Keep

@Keep
data class RegisteredUsers(
    val name: String = "",
    val phone: String = "",
    val amount: Float = 0.00f,
    val celestaid: String = ""
)


