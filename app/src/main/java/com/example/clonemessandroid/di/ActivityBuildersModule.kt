package com.example.clonemessandroid.di

import com.example.clonemessandroid.di.call.CallModule
import com.example.clonemessandroid.di.call.CallViewModelsModule
import com.example.clonemessandroid.di.camera.CameraModule
import com.example.clonemessandroid.di.camera.CameraViewModelsModule
import com.example.clonemessandroid.di.detail_chat.DetailChatModule
import com.example.clonemessandroid.di.detail_chat.DetailChatViewModelsModule
import com.example.clonemessandroid.di.detail_stories.DetailStoriesFragmentBuildersModule
import com.example.clonemessandroid.di.detail_stories.DetailStoriesModelsModule
import com.example.clonemessandroid.di.detail_stories.DetailStoriesModule
import com.example.clonemessandroid.di.edit_fullname.EditFullNameModule
import com.example.clonemessandroid.di.edit_fullname.EditFullNameViewModelsModule
import com.example.clonemessandroid.di.full_img.FullImageModule
import com.example.clonemessandroid.di.full_img.FullImageViewModelsModule
import com.example.clonemessandroid.di.home.HomeFragmentBuildersModule
import com.example.clonemessandroid.di.home.HomeModule
import com.example.clonemessandroid.di.home.HomeViewModelsModule
import com.example.clonemessandroid.di.login.LoginModule
import com.example.clonemessandroid.di.login.LoginViewModelsModule
import com.example.clonemessandroid.di.profile.ProfileModule
import com.example.clonemessandroid.di.profile.ProfileViewModelsModule
import com.example.clonemessandroid.di.register.RegisterModule
import com.example.clonemessandroid.di.register.RegisterViewModelsModule
import com.example.clonemessandroid.ui.call.CallActivity
import com.example.clonemessandroid.ui.camera.CameraActivity
import com.example.clonemessandroid.ui.detail_chat.DetailChatActivity
import com.example.clonemessandroid.ui.detail_stories.StoriesDetailActivity
import com.example.clonemessandroid.ui.edit_fullname.EditFullNameActivity
import com.example.clonemessandroid.ui.full_img.FullImageActivity
import com.example.clonemessandroid.ui.home.HomeActivity
import com.example.clonemessandroid.ui.login.LoginActivity
import com.example.clonemessandroid.ui.profile.ProfileActivity
import com.example.clonemessandroid.ui.register.RegisterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class, HomeViewModelsModule::class, HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [LoginViewModelsModule::class, LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity


    @ContributesAndroidInjector(modules = [RegisterViewModelsModule::class, RegisterModule::class])
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [ProfileViewModelsModule::class, ProfileModule::class])
    abstract fun contributeProfileActivity(): ProfileActivity


    @ContributesAndroidInjector(modules = [EditFullNameViewModelsModule::class, EditFullNameModule::class])
    abstract fun contributeEditFullNameActivity(): EditFullNameActivity

    @ContributesAndroidInjector(modules = [DetailChatViewModelsModule::class, DetailChatModule::class])
    abstract fun contributeDetailChatActivity(): DetailChatActivity

    @ContributesAndroidInjector(modules = [DetailStoriesFragmentBuildersModule::class, DetailStoriesModelsModule::class, DetailStoriesModule::class])
    abstract fun contributeStoriesDetailActivity(): StoriesDetailActivity


    @ContributesAndroidInjector(modules = [CallViewModelsModule::class,CallModule::class])
    abstract fun contributeCallActivity(): CallActivity

    @ContributesAndroidInjector(modules = [CameraViewModelsModule::class, CameraModule::class])
    abstract fun contributeCameraActivity(): CameraActivity


    @ContributesAndroidInjector(modules = [FullImageViewModelsModule::class, FullImageModule::class])
    abstract fun contributeFullImageActivity(): FullImageActivity
}