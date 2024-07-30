package com.example.neteasecloudmusic.viewmodel.activityviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse.DailySongs
class MainViewModel : ViewModel() {

    private val TAG : String = "MainModel Error"

    private val _songLiveData = MutableLiveData<DailySongs>()


    val songPicLiveData = _songLiveData.switchMap {
        Log.e(TAG,"songNameLiveData")
        MutableLiveData(it.al.picUrl)
    }

    val songNameLiveData = _songLiveData.switchMap {
        Log.e(TAG,"songNameLiveData")
        MutableLiveData(it.name)
    }

    fun getSong(dailySongs: DailySongs) {
        _songLiveData.value = dailySongs
        Log.e(TAG, _songLiveData.value!!.name)
    }



}

