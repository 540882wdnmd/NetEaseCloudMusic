package com.example.neteasecloudmusic.viewmodel.fragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscoverViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is discover Fragment"
    }
    val text: LiveData<String> = _text
}