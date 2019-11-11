package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageApi{

    @GET("user/{name}")
    abstract fun getUserTest(@Path("name") name: String): Observable<UserModel>


}