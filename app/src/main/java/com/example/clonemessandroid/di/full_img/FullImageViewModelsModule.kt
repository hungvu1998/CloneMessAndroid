package com.example.clonemessandroid.di.full_img

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.full_img.FullImageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FullImageViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(FullImageViewModel::class)
    abstract fun bindFullImageViewModel(fullImageViewModel: FullImageViewModel): ViewModel
}