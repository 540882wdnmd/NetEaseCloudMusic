package com.example.neteasecloudmusic.model.data

data class SongResponse(val code : Int,val data : List<Data>){
    data class Data(val id: Int,val url : String )
}