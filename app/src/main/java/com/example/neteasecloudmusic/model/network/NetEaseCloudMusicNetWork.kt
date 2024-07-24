package com.example.neteasecloudmusic.model.network

import com.example.neteasecloudmusic.model.data.SongResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetEaseCloudMusicNetWork {

    private suspend fun <T> Call<T>.await() : T{
        return suspendCoroutine {continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body!=null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    private val loginService : LoginService = ServiceCreator.create<LoginService>()

    private val dailyRecommendService : DailyRecommendService = ServiceCreator.create<DailyRecommendService>()

    private val songService : SongService = ServiceCreator.create<SongService>()

    private val logoutService : LogoutService = ServiceCreator.create<LogoutService>()

    suspend fun getCellphoneLoginResponse(phone : String,password : String) = loginService.cellphoneLogin(phone, password).await()

    suspend fun getEmailLoginResponse(email : String,password: String) = loginService.emailLogin(email, password).await()

    suspend fun getQrKey(timestamp : Long) = loginService.qrcodeRequestKey(timestamp).await()

    suspend fun getQrCreateResponse(key : String,timestamp: Long) = loginService.qrcodeCreate(key, timestamp).await()

    suspend fun getQrCheckResponse(key : String,timestamp: Long) = loginService.qrcodeCheck(key, timestamp).await()

    suspend fun getDailyRecommendSongsResponse(cookie: String) = dailyRecommendService.getDailyRecommendSongs(cookie).await()

    suspend fun getSongResponse(id : Long) = songService.getSong(id).await()

    suspend fun getLogoutResponse() = logoutService.logout().await()
}

