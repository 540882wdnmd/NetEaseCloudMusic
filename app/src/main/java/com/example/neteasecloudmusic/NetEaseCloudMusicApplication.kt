package com.example.neteasecloudmusic

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class NetEaseCloudMusicApplication : Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var instance : Application
    }

    init {
        instance = this
    }
}