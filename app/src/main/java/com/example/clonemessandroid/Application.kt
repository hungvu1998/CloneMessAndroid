package com.example.clonemessandroid

import dagger.android.AndroidInjector
import com.example.clonemessandroid.di.AppComponent
import com.example.clonemessandroid.di.DaggerAppComponent
import dagger.android.support.DaggerApplication

class Application : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}