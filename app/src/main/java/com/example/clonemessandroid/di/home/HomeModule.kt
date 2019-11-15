package com.example.clonemessandroid.di.home

import com.example.clonemessandroid.network.DetailChatApi
import com.example.clonemessandroid.network.MessageApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class HomeModule{
    @Provides
    fun provideMessageApi(retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }
    @Provides
    fun provideDetailChatApi(retrofit: Retrofit): DetailChatApi {
        return retrofit.create(DetailChatApi::class.java)
    }
}