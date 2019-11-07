package `in`.org.celesta.admin.ui.EventUsers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EventUsersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "List of All registered users/Teams"
    }
    val text: LiveData<String> = _text
}