package com.example.clonemessandroid.di

import com.example.clonemessandroid.di.home.HomeFragmentBuildersModule
import com.example.clonemessandroid.di.home.HomeModule
import com.example.clonemessandroid.di.home.HomeViewModelsModule
import com.example.clonemessandroid.di.login.LoginModule
import com.example.clonemessandroid.di.login.LoginViewModelsModule
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

}