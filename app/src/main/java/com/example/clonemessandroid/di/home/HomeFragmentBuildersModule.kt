package com.example.clonemessandroid.di.home

import com.example.clonemessandroid.ui.home.local.LocalFragment
import com.example.clonemessandroid.ui.home.message.MessageFragment
import com.example.clonemessandroid.ui.home.story.StoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {


    @ContributesAndroidInjector
    abstract fun constributeMessageFragment():MessageFragment
    @ContributesAndroidInjector
    abstract fun constributeStoryFragment(): StoryFragment
    @ContributesAndroidInjector
    abstract fun constributeLocalFragment(): LocalFragment
}