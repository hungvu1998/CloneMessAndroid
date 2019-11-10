package com.example.clonemessandroid.di.login

import com.example.clonemessandroid.network.LoginApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class LoginModule{
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }
}