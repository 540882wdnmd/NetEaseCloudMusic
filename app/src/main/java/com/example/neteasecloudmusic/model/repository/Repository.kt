package com.example.neteasecloudmusic.model.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.neteasecloudmusic.model.network.NetEaseCloudMusicNetWork
import kotlinx.coroutines.Dispatchers
import okhttp3.Cookie
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.math.tan

object Repository {

    private const val TAG = "Repository Error"

    fun getCellPhoneLoginResponse(phone : String,password : String) = fire(Dispatchers.IO){
        val loginResponse = NetEaseCloudMusicNetWork.getCellphoneLoginResponse(phone, password)
        if (loginResponse.code==200){
            Result.success(loginResponse)
        }else{
            Log.e(TAG,"getCellphoneLoginResponse fail")
            Result.failure(RuntimeException("Login Request Fail"))
        }
    }

    fun getLogoutResponse() = fire(Dispatchers.IO){
        val logoutResponse = NetEaseCloudMusicNetWork.getLogoutResponse()
        if (logoutResponse.code == 200){
            Result.success(logoutResponse)
        }else{
            Log.e(TAG,"getLogoutResponse fail")
            Result.failure(RuntimeException("Logout Request Fail"))
        }
    }

    fun getSongResponse(id : Long) = fire(Dispatchers.IO){
        val songResponse = NetEaseCloudMusicNetWork.getSongResponse(id)
        if (songResponse.code == 200){
            Result.success(songResponse.data)
        }else{
            Log.e(TAG,"getSongResponse fail")
            Result.failure(RuntimeException("Song Request Fail"))
        }
    }

    fun getDailyRecommendSongsResponse(cookie: String) = fire(Dispatchers.IO){
        val songsResponse = NetEaseCloudMusicNetWork.getDailyRecommendSongsResponse(cookie)
        if (songsResponse.code == 200){
            Result.success(songsResponse.data.dailySongs)
        }else{
            Log.e(TAG,"getDailyRecommendSongsResponse fail")
            Result.failure(RuntimeException("Daily Recommend Songs Request Fail"))
        }
    }

    fun getEmailLoginResponse(email : String,password: String) = fire(Dispatchers.IO){
        val loginResponse = NetEaseCloudMusicNetWork.getEmailLoginResponse(email, password)
        if (loginResponse.code==200){
            Result.success(loginResponse)
        }else{
            Result.failure(RuntimeException("Login Request Fail"))
        }
    }


    private fun <T> fire(context : CoroutineContext, block : suspend () -> Result<T>)=
        liveData(context) {
            val result = try {
                block()
            } catch (e:Exception){
                Log.e(TAG,e.message,e)
                Result.failure(e)
            }
            emit(result)
        }


    fun downloadImage(urlString: String): ByteArray? {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000 // 设置连接超时时间
        connection.readTimeout = 5000 // 设置读取超时时间
        connection.requestMethod = "GET"

        try {
            val inputStream = connection.inputStream
            return inputStream.readBytes()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection.disconnect()
        }
        return null
    }
}