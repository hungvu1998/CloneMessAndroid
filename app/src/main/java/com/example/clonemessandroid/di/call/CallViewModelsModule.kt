package com.example.clonemessandroid.di.call

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.call.CallViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CallViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(CallViewModel::class)
    abstract fun bindCallViewModel(callViewModel: CallViewModel): ViewModel
}