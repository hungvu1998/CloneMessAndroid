package com.example.clonemessandroid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class VideoChatModel{
    @SerializedName("from")
    @Expose
    var from:String?=null

    @SerializedName("to")
    @Expose
    var to:String?=null

    @SerializedName("apiKey")
    @Expose
    var apiKey:String?=null

    @SerializedName("sessionId")
    @Expose
    var sessionId:String?=null

    @SerializedName("token")
    @Expose
    var token:String?=null
}


