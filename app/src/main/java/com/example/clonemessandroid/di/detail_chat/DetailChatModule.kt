package com.example.clonemessandroid.di.detail_chat

import com.example.clonemessandroid.network.DetailChatApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DetailChatModule {
    @Provides
    fun provideDetailChatApi(retrofit: Retrofit): DetailChatApi {
        return retrofit.create(DetailChatApi::class.java)
    }
}