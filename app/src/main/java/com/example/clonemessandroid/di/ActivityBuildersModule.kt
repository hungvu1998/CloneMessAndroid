package com.example.clonemessandroid.di

import com.example.clonemessandroid.di.detail_stories.DetailStoriesFragmentBuildersModule
import com.example.clonemessandroid.di.detail_stories.DetailStoriesModelsModule
import com.example.clonemessandroid.di.detail_stories.DetailStoriesModule
import com.example.clonemessandroid.di.home.HomeFragmentBuildersModule
import com.example.clonemessandroid.di.home.HomeModule
import com.example.clonemessandroid.di.home.HomeViewModelsModule
import com.example.clonemessandroid.di.login.LoginModule
import com.example.clonemessandroid.di.login.LoginViewModelsModule
import com.example.clonemessandroid.ui.detail_stories.StoriesDetailActivity
import com.example.clonemessandroid.ui.home.HomeActivity
import com.example.clonemessandroid.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {


    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class, HomeViewModelsModule::class, HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [LoginViewModelsModule::class, LoginModule::class])
    abstract fun contributeLoginActivity(): LoginActivity


    @ContributesAndroidInjector(modules = [DetailStoriesFragmentBuildersModule::class, DetailStoriesModelsModule::class, DetailStoriesModule::class])
    abstract fun contributeStoriesDetailActivity(): StoriesDetailActivity
}