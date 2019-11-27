package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import retrofit2.http.*

interface LoginApi{
    @POST("login")
    @FormUrlEncoded
    abstract fun login(@Field("username") name:String,@Field("password") password:String) : Observable<UserModel>

    @GET("user/{name}")
    abstract fun getUser(@Path("name") name: String): Observable<UserModel>

}