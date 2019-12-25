package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.data.model.ChatModel
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface DetailChatApi{
    @GET("message/{idChat}/{page}")
    abstract fun getListChatApi(@Path("idChat") idChat: String,@Path("page") page:Int) : Observable<ChatModel>

    @GET("stories/{idStories}")
    abstract fun getDetailStories(@Path("idStories") idStories: String) : Observable<Stories>

    @POST("stories")
    @FormUrlEncoded
    abstract fun postDetailStories(@Field("imagePath") imagePath:String) : Observable<Stories>

    @Multipart
    @POST("upload")
    abstract fun upImage(@Part files: MultipartBody.Part ) : Single<ResponseBody>

    @PUT("user/{name}")
    @FormUrlEncoded
    abstract fun addStoryIntoUser(@Path("name") name: String,@Field("fullname") fullname:String?) : Observable<UserModel>

}
