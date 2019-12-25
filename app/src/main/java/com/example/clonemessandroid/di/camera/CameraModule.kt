package com.example.clonemessandroid.di.camera

import com.example.clonemessandroid.network.DetailChatApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CameraModule {
    @Provides
    fun provideDetailChatApi(retrofit: Retrofit): DetailChatApi {
        return retrofit.create(DetailChatApi::class.java)
    }
}