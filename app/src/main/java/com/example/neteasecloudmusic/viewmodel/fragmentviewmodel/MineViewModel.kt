package com.example.neteasecloudmusic.viewmodel.fragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MineViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is mine Fragment"
    }
    val text: LiveData<String> = _text
}