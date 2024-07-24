package com.example.neteasecloudmusic.model.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.example.neteasecloudmusic.NetEaseCloudMusicApplication
import com.example.neteasecloudmusic.model.data.Account
import com.example.neteasecloudmusic.model.data.User

object SharedPreferencesHelper {

    private const val PREF_NAME : String = "user_inform"

    private val sharedPreferences = NetEaseCloudMusicApplication.instance.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    private const val  NICKNAME : String = "nickname"

    private const val  AVATAR_IMAGE_URL : String = "avatarImageUrl"

    private const val COOKIE : String = "cookie"

    private const val LOGIN_STATUS = "login_status"

    private const val DEFAULT_STRING = "NULL"

    private const val DEFAULT_BOOLEAN = false

    private const val DEFAULT_INT = 0


    private fun putString(key: String, value: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    private fun getString(key: String) : String? {
        return sharedPreferences?.getString(key, this.DEFAULT_STRING)
    }

    private fun putBoolean(key: String, value: Boolean){
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key,value)
        editor?.apply()
    }

    private fun getBoolean(key: String): Boolean?{
        return sharedPreferences?.getBoolean(key, DEFAULT_BOOLEAN)
    }


    private fun remove(key: String){
        val editor = sharedPreferences?.edit()
        editor?.remove(key)
        editor?.apply()
    }

    fun clear(){
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }

    fun login(account: Account){
        putBoolean(LOGIN_STATUS,true)
        putString(NICKNAME,account.nickName)
        putString(AVATAR_IMAGE_URL,account.avatarImageUrl)
        putString(COOKIE,account.cookie)
    }

    fun getLoginStatus() : Boolean? {
        return SharedPreferencesHelper.getBoolean(LOGIN_STATUS)
    }

    fun logout(){
        putBoolean(LOGIN_STATUS,false)
        remove(NICKNAME)
        remove(AVATAR_IMAGE_URL)
        remove(COOKIE)
    }

    fun getAccountNickName(): String? {
        return getString(NICKNAME)
    }

    fun getAvatarImageUrl(): String? {
        return getString(AVATAR_IMAGE_URL)
    }

    fun getCookie() : String?{
        return getString(COOKIE)
    }
}