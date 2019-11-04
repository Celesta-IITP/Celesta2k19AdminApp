package com.example.celesta2k19admin.ui.AllEvents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllEventsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "List of All Events in Celesta 2k19"
    }
    val text: LiveData<String> = _text
}