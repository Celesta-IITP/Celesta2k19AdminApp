package `in`.org.celesta.admin.ui.AllEvents

import `in`.org.celesta.admin.api.AllEvents
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllEventsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "List of All Events in Celesta 2k19"
    }

    private val eventsData = MutableLiveData<List<AllEvents>>().apply {
        value = ArrayList()
    }

    val text: LiveData<String> = _text

    fun getEventData(): LiveData<List<AllEvents>> {
        return eventsData;
    }

    fun setEventData(data: List<AllEvents>) {
        eventsData.value = data
    }

}