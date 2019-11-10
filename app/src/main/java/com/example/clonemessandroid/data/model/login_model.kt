package com.example.clonemessandroid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel{
    @SerializedName("message")
    @Expose
    var message:Boolean?=null


    @SerializedName("data")
    @Expose
    var userModel:UserModel?=null
}