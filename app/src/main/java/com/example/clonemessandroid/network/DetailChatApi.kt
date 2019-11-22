package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.ChatModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface DetailChatApi{
    @GET("message/{idChat}")
    abstract fun getListChat(@Path("idChat") idChat: String) : Observable<ChatModel>

    @Multipart
    @POST("upload")
    abstract fun upImage(@Part image: MultipartBody.Part ) : Observable<ResponseBody>

}