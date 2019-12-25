package com.example.clonemessandroid.network

import com.example.clonemessandroid.data.model.UserModel
import io.reactivex.Observable
import retrofit2.http.*

interface EditProfileApi{
    @PUT("user/{name}")
    @FormUrlEncoded
    abstract fun editProfile(@Path("name") name: String,@Field("fullname") fullname:String?, @Field("avatar") avatar:String?, @Field("email") email:String?) : Observable<UserModel>



}