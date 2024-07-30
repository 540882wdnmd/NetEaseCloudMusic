package com.example.neteasecloudmusic.viewmodel.fragmentviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import com.example.neteasecloudmusic.model.data.Song

class MusicPlayerBottomSheetDialogViewModel : ViewModel() {
    private val TAG = "MusicPlayerViewModel"

    private val _musicUrlLiveData = MutableLiveData<String>()

    val musicUrlLiveData = _musicUrlLiveData.switchMap {
        Log.e(TAG,"ERROR")
        MutableLiveData(it)
    }

    fun playMusic(musicUrl : String){
        _musicUrlLiveData.value = musicUrl
        Log.e(TAG,musicUrl)
    }
}