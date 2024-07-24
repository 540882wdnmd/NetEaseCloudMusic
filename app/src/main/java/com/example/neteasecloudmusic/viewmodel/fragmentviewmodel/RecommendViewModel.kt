package com.example.neteasecloudmusic.viewmodel.fragmentviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import com.example.neteasecloudmusic.model.data.Song
import com.example.neteasecloudmusic.model.repository.Repository

class RecommendViewModel : ViewModel() {

    private val _dailyRecommendSongLiveData = MutableLiveData<String>()

    val songList = ArrayList<DailyRecommendSongsResponse.DailySongs>()

    val dailyRecommendSongLiveData = _dailyRecommendSongLiveData.switchMap {
        Repository.getDailyRecommendSongsResponse(it)
    }

    fun getDailyRecommendSongLiveData(cookie : String){
        _dailyRecommendSongLiveData.value = cookie
    }

}