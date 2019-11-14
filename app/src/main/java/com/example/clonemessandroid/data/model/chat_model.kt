package com.example.clonemessandroid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class  ChatDetailModel{
    @SerializedName("from")
    @Expose
    var from:String?=null


    @SerializedName("to")
    @Expose
    var to:String?=null

    @SerializedName("content")
    @Expose
    var content:String?=null

    @SerializedName("type")
    @Expose
    var type:Long?=null

    @SerializedName("timestamp")
    @Expose
    var timestamp:Long?=null


    var idChat :String ?=null
}


class ChatModel{
    @SerializedName("_id")
    @Expose
    var _id:String?=null


    @SerializedName("messages")
    @Expose
    var messages:List<ChatDetailModel>?=null


}