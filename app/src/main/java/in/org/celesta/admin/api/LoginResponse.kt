package `in`.org.celesta.admin.api

import androidx.annotation.Keep

@Keep
data class LoginResponse(
    private val status: Int = 0,
    private val access_token: String = "",
    private val permit: String = "",
    private val position: String = "",
    private val email: String = "",
    private val message: List<String>
) {
    fun status(): Int {
        return status
    }

    fun access_token(): String {
        return access_token
    }

    fun permit(): String {
        return permit
    }

    fun position(): String {
        return position
    }

    fun email(): String {
        return email
    }

}