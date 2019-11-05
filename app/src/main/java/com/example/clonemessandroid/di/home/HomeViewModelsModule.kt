package com.example.clonemessandroid.di.home

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.home.HomeViewModel
import com.example.clonemessandroid.ui.home.local.LocalViewModel
import com.example.clonemessandroid.ui.home.message.MessageViewModel
import com.example.clonemessandroid.ui.home.story.StoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelsModule {


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun binHomeViewModel(viewModel: HomeViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(viewModel: MessageViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    abstract fun bindStoryViewModel(viewModel: StoryViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LocalViewModel::class)
    abstract fun bindLocalViewModel(viewModel: LocalViewModel): ViewModel
}