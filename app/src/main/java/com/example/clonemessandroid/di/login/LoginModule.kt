package com.example.clonemessandroid.di.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.clonemessandroid.network.LoginApi
import com.example.clonemessandroid.ui.login.LoginActivity
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class LoginModule{
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

}