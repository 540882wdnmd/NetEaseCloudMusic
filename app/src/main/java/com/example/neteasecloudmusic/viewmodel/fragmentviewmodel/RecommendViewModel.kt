package com.example.neteasecloudmusic.viewmodel.fragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import com.example.neteasecloudmusic.model.data.Song
import com.example.neteasecloudmusic.model.repository.Repository

class RecommendViewModel : ViewModel() {

    private var _dailyRecommendSongLiveData = MutableLiveData<String>()

    private var _songLiveData = MutableLiveData<Long>()

    val songList = ArrayList<DailyRecommendSongsResponse.DailySongs>()

    val dailyRecommendSongLiveData = _dailyRecommendSongLiveData.switchMap {
        Repository.getDailyRecommendSongsResponse(it)
    }

    val songLiveData = _songLiveData.switchMap {
        Repository.getSongResponse(it)
    }

    fun getDailyRecommendSongResponse(cookie : String){
        _dailyRecommendSongLiveData.value = cookie
    }

    fun getSongResponse(id : Long){
        _songLiveData.value = id
    }



}