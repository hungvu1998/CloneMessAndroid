package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface MessageApi{


    @GET("user/{name}")
    abstract fun getUser(@Path("name") name: String): Observable<UserModel>


    @Multipart
    @POST("upload")
    abstract fun upImage(@Part files: MultipartBody.Part ) : Single<ResponseBody>

    @PUT("user/{name}")
    @FormUrlEncoded
    abstract fun editProfile(@Path("name") name: String,@Field("fullname") fullname:String?, @Field("avatar") avatar:String?) : Observable<UserModel>
}