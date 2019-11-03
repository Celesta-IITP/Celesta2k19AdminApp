package com.example.celesta2k19admin.api

import com.google.gson.annotations.SerializedName

data class CheckinCheckoutResponse(
    @SerializedName("status")
    private val status: String = "",
    @SerializedName("message")
    private val message: List<String>,
    @SerializedName("action")
    private val action: String = ""
) {
    fun status(): String {
        return status
    }

    fun action(): String {
        return action
    }


}