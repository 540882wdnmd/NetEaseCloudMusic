package com.example.neteasecloudmusic.model.data

data class LoginResponse(val loginType : Int,val code : Int, val token : String,val profile : Profile,val cookie : String,val message : String){
    data class Profile(val userType : Int, val avatarUrl : String, val nickname : String,val userId : Long)
}

data class Key(val data : Data , val code : Int){
    data class Data(val code : Int, val unikey : String)
}

data class QrCreate(val code : Int,val data : Data){
    data class Data(val qrurl : String,val qrimg : String)
}

data class QrCheck(val code : Int , val message : String,val cookie: String)

