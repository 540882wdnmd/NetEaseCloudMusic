package com.example.neteasecloudmusic.model.network


import com.example.neteasecloudmusic.model.data.LogoutResponse
import retrofit2.Call
import retrofit2.http.GET

interface LogoutService {

    @GET("logout")
    fun logout() : Call<LogoutResponse>
}