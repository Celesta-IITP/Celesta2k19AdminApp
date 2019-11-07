package `in`.org.celesta.admin.api

import androidx.annotation.Keep

@Keep
data class AllEventsResponse(
    private val status: Int = 0,
    private val message: String = "",
    private val events: List<AllEvents> = listOf()
) {

    fun getStatus(): Int {
        return status
    }

    fun getMessage(): String {
        return message
    }

    fun getEvents(): List<AllEvents> {
        return events
    }

}