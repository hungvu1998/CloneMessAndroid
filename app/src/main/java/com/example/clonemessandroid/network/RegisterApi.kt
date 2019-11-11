package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApi{
    @POST("register")
    @FormUrlEncoded
    abstract fun register(@Field("username") name:String, @Field("password") password:String) : Observable<UserModel>
}