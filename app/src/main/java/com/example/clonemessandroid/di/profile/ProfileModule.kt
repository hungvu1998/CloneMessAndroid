package com.example.clonemessandroid.di.profile

import com.example.clonemessandroid.network.MessageApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProfileModule{
    @Provides
    fun provideMessageApi(retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }
}