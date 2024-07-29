package com.example.neteasecloudmusic.model.data

import android.util.Log

data class DailyRecommendSongsResponse(val msg : String, val code : Int, val data : Data){

    data class Data(val dailySongs : List<DailySongs>,val mvResourceInfos : String,val demote : Boolean)

    data class DailySongs(val name :String,val id : Long,val ar : List<Artists>,val al : Al)

    data class Artists(val id : Int,val name: String)

    data class Al(val id : Long,val name: String,val picUrl : String)

}
