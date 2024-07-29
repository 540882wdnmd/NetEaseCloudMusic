package com.example.neteasecloudmusic.viewmodel.activityviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse.DailySongs
import com.example.neteasecloudmusic.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class MainViewModel : ViewModel() {

    private val TAG : String = "MainModel Error"
    private val _songLiveData = MutableLiveData<DailySongs>()

    val songPicLiveData = _songLiveData.switchMap {
        Log.e(TAG,"songNameLiveData")
        liveData(Dispatchers.Main){
            runBlocking {
                Log.e(TAG,"songPicLiveData")
                val result = async {
                    it.al.picUrl
                }.await()
                emit(Result.success(result))
            }
        }
    }

    val songNameLiveData = _songLiveData.switchMap { it ->
        Log.e(TAG,"songNameLiveData")
        liveData(Dispatchers.Main){
            runBlocking {
                Log.e(TAG,"songNameLiveData")
                val result = async {
                    it.name
                }.await()
                emit(Result.success(result))
            }
        }
    }

    fun getSong(dailySongs: DailySongs) {
        _songLiveData.value = dailySongs
        Log.e("MainModel", _songLiveData.value!!.name)
    }


}

