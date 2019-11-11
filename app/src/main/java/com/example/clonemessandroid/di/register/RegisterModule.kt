package com.example.clonemessandroid.di.register

import com.example.clonemessandroid.network.RegisterApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RegisterModule{
    @Provides
    fun provideLoginApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }
}