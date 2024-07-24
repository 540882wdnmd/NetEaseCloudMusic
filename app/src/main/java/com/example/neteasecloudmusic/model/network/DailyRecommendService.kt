package com.example.neteasecloudmusic.model.network

import com.example.neteasecloudmusic.model.data.DailyRecommendSongsResponse
import okhttp3.Cookie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface DailyRecommendService {

    @GET("recommend/songs")
    @Headers("Content-Type: application/json")
    fun getDailyRecommendSongs(@Header("cookie") cookie: String) : Call<DailyRecommendSongsResponse>
}