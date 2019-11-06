package com.example.clonemessandroid.di.detail_stories

import com.example.clonemessandroid.ui.home.local.LocalFragment
import com.example.clonemessandroid.ui.home.message.MessageFragment
import com.example.clonemessandroid.ui.home.story.StoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailStoriesFragmentBuildersModule {


    @ContributesAndroidInjector
    abstract fun constributeStoryFragment(): StoryFragment

}