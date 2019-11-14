package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.ChatModel
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetailChatApi{
    @GET("message/{idChat}")
    abstract fun getListChat(@Path("idChat") idChat: String) : Observable<ChatModel>
}