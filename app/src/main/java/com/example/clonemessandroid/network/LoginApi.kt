package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.LoginModel
import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import retrofit2.http.*

interface LoginApi{

    // /posts?userId=1/
//    @POST("login")
//    abstract fun getPostsFromUser(@Path("name") name: String): Observable<UserModel>
    @GET("user/{name}")
    abstract fun getUserTest(@Path("name") name: String): Observable<UserModel>

    @POST("login")
    @FormUrlEncoded
    abstract fun login(@Field("username") name:String,@Field("password") password:String) : Observable<UserModel>
}