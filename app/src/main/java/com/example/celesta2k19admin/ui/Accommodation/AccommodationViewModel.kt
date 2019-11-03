package com.example.celesta2k19admin.ui.Accommodation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccommodationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Click on button to scan QR code to see status of user accommodation"
    }
    val text: LiveData<String> = _text
}