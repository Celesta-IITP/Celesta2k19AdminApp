package `in`.org.celesta.admin.ui.CheckinCheckout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckinCheckoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Click on button to checkin or checkout user by scanning QR code"
    }
    val text: LiveData<String> = _text
}