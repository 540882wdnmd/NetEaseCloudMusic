package com.example.neteasecloudmusic.viewmodel.fragmentviewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.neteasecloudmusic.model.data.User
import com.example.neteasecloudmusic.model.repository.Repository


class DrawerViewModel : ViewModel() {

    private val _loginResponseLiveData = MutableLiveData<User>()

    private val _logoutResponseLiveData = MutableLiveData<Boolean>()

    val loginResponseLiveData = _loginResponseLiveData.switchMap {
            user -> Repository.getCellPhoneLoginResponse(user.account,user.password)
    }

    val logoutResponseLiveData = _logoutResponseLiveData.switchMap {
        Repository.getLogoutResponse()
    }
    fun getLoginResponse(phone : String,password : String){
        _loginResponseLiveData.value = User(phone,password)
        Log.e("DrawerViewModel",phone)
    }

    fun getLogoutResponse(){
        _logoutResponseLiveData.value = _logoutResponseLiveData.value != true
    }


}