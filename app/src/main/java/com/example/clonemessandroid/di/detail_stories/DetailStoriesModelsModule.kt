package com.example.clonemessandroid.di.detail_stories

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.detail_stories.DetailStoriesViewModel
import com.example.clonemessandroid.ui.detail_stories.story_fragment.StoryDetailFragmentViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailStoriesModelsModule {


    @Binds
    @IntoMap
    @ViewModelKey(DetailStoriesViewModel::class)
    abstract fun bindDetailStoriesViewModel(viewModel: DetailStoriesViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(StoryDetailFragmentViewModel::class)
    abstract fun bindStoryDetailFragmentViewModel(viewModel: StoryDetailFragmentViewModel): ViewModel




}