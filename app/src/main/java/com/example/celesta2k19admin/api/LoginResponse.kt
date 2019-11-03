package com.example.celesta2k19admin.api

data class LoginResponse(
    private val status: Int = 0,
    private val access_token: String = "",
    private val permit: String = "",
    private val position: String = "",
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

}