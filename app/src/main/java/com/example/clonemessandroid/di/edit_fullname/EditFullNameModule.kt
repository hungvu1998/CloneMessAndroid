package com.example.clonemessandroid.di.edit_fullname

import com.example.clonemessandroid.network.EditProfileApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class EditFullNameModule{
    @Provides
    fun provideEditProfileApi(retrofit: Retrofit): EditProfileApi {
        return retrofit.create(EditProfileApi::class.java)
    }
}