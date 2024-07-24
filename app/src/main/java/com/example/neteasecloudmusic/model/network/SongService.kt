package com.example.neteasecloudmusic.model.network


import com.example.neteasecloudmusic.model.data.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SongService {

    @GET("song/url")
    fun getSong(@Query("id") id : Long) : Call<SongResponse>
}