package com.example.neteasecloudmusic.model.network

import com.example.neteasecloudmusic.model.data.Key
import com.example.neteasecloudmusic.model.data.LoginResponse
import com.example.neteasecloudmusic.model.data.QrCheck
import com.example.neteasecloudmusic.model.data.QrCreate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.security.Timestamp

interface LoginService {

    @POST("login/cellphone")
    fun cellphoneLogin(@Query("phone") phone : String,@Query("password") password : String) : Call<LoginResponse>

    @POST("login")
    fun emailLogin(@Query("email") email : String,@Query("password") password: String) : Call<LoginResponse>

    @GET("login/qr/key")
    fun qrcodeRequestKey(@Query("timestamp") timestamp: Long) : Call<Key>

    @GET("login/qr/create")
    fun qrcodeCreate(@Query("key") key : String,@Query("timestamp") timestamp: Long) : Call<QrCreate>

    @GET("login/qr/check")
    fun qrcodeCheck(@Query("key") key : String,@Query("timestamp") timestamp: Long) : Call<QrCheck>

}