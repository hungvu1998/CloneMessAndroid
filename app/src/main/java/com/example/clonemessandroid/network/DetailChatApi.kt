package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.ChatModel
import com.example.clonemessandroid.data.model.Stories
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface DetailChatApi{
    @GET("message/{idChat}")
    abstract fun getListChat(@Path("idChat") idChat: String) : Observable<ChatModel>

    @GET("stories/{idStories}")
    abstract fun getDetailStories(@Path("idStories") idStories: String) : Observable<Stories>
    @Multipart
    @POST("upload")
    abstract fun upImage(@Part image: MultipartBody.Part ) : Single<ResponseBody>



}